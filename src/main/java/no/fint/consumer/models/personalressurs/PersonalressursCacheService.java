package no.fint.consumer.models.personalressurs;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

import no.fint.cache.CacheService;
import no.fint.cache.model.CacheObject;
import no.fint.consumer.config.Constants;
import no.fint.consumer.config.ConsumerProps;
import no.fint.consumer.event.ConsumerEventUtil;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.relations.FintResourceCompatibility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import no.fint.model.administrasjon.personal.Personalressurs;
import no.fint.model.resource.administrasjon.personal.PersonalressursResource;
import no.fint.model.administrasjon.personal.PersonalActions;
import no.fint.model.felles.kompleksedatatyper.Identifikator;

@Slf4j
@Service
@ConditionalOnProperty(name = "fint.consumer.cache.disabled.personalressurs", havingValue = "false", matchIfMissing = true)
public class PersonalressursCacheService extends CacheService<PersonalressursResource> {

    public static final String MODEL = Personalressurs.class.getSimpleName().toLowerCase();

    @Value("${fint.consumer.compatibility.fintresource:true}")
    private boolean checkFintResourceCompatibility;

    @Autowired
    private FintResourceCompatibility fintResourceCompatibility;

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    @Autowired
    private PersonalressursLinker linker;

    private JavaType javaType;

    private ObjectMapper objectMapper;

    public PersonalressursCacheService() {
        super(MODEL, PersonalActions.GET_ALL_PERSONALRESSURS, PersonalActions.UPDATE_PERSONALRESSURS);
        objectMapper = new ObjectMapper();
        javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, PersonalressursResource.class);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @PostConstruct
    public void init() {
        props.getAssets().forEach(this::createCache);
    }

    @Scheduled(initialDelayString = Constants.CACHE_INITIALDELAY_PERSONALRESSURS, fixedRateString = Constants.CACHE_FIXEDRATE_PERSONALRESSURS)
    public void populateCacheAll() {
        props.getAssets().forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
		flush(orgId);
		populateCache(orgId);
	}

    @Override
    public void populateCache(String orgId) {
		log.info("Populating Personalressurs cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, PersonalActions.GET_ALL_PERSONALRESSURS, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }


    public Optional<PersonalressursResource> getPersonalressursByAnsattnummer(String orgId, String ansattnummer) {
        return getOne(orgId, ansattnummer.hashCode(),
            (resource) -> Optional
                .ofNullable(resource)
                .map(PersonalressursResource::getAnsattnummer)
                .map(Identifikator::getIdentifikatorverdi)
                .map(ansattnummer::equals)
                .orElse(false));
    }

    public Optional<PersonalressursResource> getPersonalressursByBrukernavn(String orgId, String brukernavn) {
        return getOne(orgId, brukernavn.hashCode(),
            (resource) -> Optional
                .ofNullable(resource)
                .map(PersonalressursResource::getBrukernavn)
                .map(Identifikator::getIdentifikatorverdi)
                .map(brukernavn::equals)
                .orElse(false));
    }

    public Optional<PersonalressursResource> getPersonalressursBySystemId(String orgId, String systemId) {
        return getOne(orgId, systemId.hashCode(),
            (resource) -> Optional
                .ofNullable(resource)
                .map(PersonalressursResource::getSystemId)
                .map(Identifikator::getIdentifikatorverdi)
                .map(systemId::equals)
                .orElse(false));
    }


	@Override
    public void onAction(Event event) {
        List<PersonalressursResource> data;
        if (checkFintResourceCompatibility && fintResourceCompatibility.isFintResourceData(event.getData())) {
            log.info("Compatibility: Converting FintResource<PersonalressursResource> to PersonalressursResource ...");
            data = fintResourceCompatibility.convertResourceData(event.getData(), PersonalressursResource.class);
        } else {
            data = objectMapper.convertValue(event.getData(), javaType);
        }
        data.forEach(linker::mapLinks);
        if (PersonalActions.valueOf(event.getAction()) == PersonalActions.UPDATE_PERSONALRESSURS) {
            if (event.getResponseStatus() == ResponseStatus.ACCEPTED || event.getResponseStatus() == ResponseStatus.CONFLICT) {
                List<CacheObject<PersonalressursResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
                addCache(event.getOrgId(), cacheObjects);
                log.info("Added {} cache objects to cache for {}", cacheObjects.size(), event.getOrgId());
            } else {
                log.debug("Ignoring payload for {} with response status {}", event.getOrgId(), event.getResponseStatus());
            }
        } else {
            List<CacheObject<PersonalressursResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
            updateCache(event.getOrgId(), cacheObjects);
            log.info("Updated cache for {} with {} cache objects", event.getOrgId(), cacheObjects.size());
        }
    }
}
