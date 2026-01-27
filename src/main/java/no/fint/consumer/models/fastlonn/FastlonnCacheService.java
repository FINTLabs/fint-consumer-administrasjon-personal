package no.fint.consumer.models.fastlonn;

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

import no.novari.fint.model.administrasjon.personal.Fastlonn;
import no.novari.fint.model.resource.administrasjon.personal.FastlonnResource;
import no.novari.fint.model.administrasjon.personal.PersonalActions;
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator;

@Slf4j
@Service
@ConditionalOnProperty(name = "fint.consumer.cache.disabled.fastlonn", havingValue = "false", matchIfMissing = true)
public class FastlonnCacheService extends CacheService<FastlonnResource> {

    public static final String MODEL = Fastlonn.class.getSimpleName().toLowerCase();

    @Value("${fint.consumer.compatibility.fintresource:true}")
    private boolean checkFintResourceCompatibility;

    @Autowired
    private FintResourceCompatibility fintResourceCompatibility;

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    @Autowired
    private FastlonnLinker linker;

    private JavaType javaType;

    private ObjectMapper objectMapper;

    public FastlonnCacheService() {
        super(MODEL, PersonalActions.GET_ALL_FASTLONN, PersonalActions.UPDATE_FASTLONN);
        objectMapper = new ObjectMapper();
        javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, FastlonnResource.class);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @PostConstruct
    public void init() {
        props.getAssets().forEach(this::createCache);
    }

    @Scheduled(initialDelayString = Constants.CACHE_INITIALDELAY_FASTLONN, fixedRateString = Constants.CACHE_FIXEDRATE_FASTLONN)
    public void populateCacheAll() {
        props.getAssets().forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
		flush(orgId);
		populateCache(orgId);
	}

    @Override
    public void populateCache(String orgId) {
		log.info("Populating Fastlonn cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, PersonalActions.GET_ALL_FASTLONN, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }


    public Optional<FastlonnResource> getFastlonnByKildesystemId(String orgId, String kildesystemId) {
        return getOne(orgId, kildesystemId.hashCode(),
            (resource) -> Optional
                .ofNullable(resource)
                .map(FastlonnResource::getKildesystemId)
                .map(Identifikator::getIdentifikatorverdi)
                .map(kildesystemId::equals)
                .orElse(false));
    }

    public Optional<FastlonnResource> getFastlonnBySystemId(String orgId, String systemId) {
        return getOne(orgId, systemId.hashCode(),
            (resource) -> Optional
                .ofNullable(resource)
                .map(FastlonnResource::getSystemId)
                .map(Identifikator::getIdentifikatorverdi)
                .map(systemId::equals)
                .orElse(false));
    }


	@Override
    public void onAction(Event event) {
        List<FastlonnResource> data;
        if (checkFintResourceCompatibility && fintResourceCompatibility.isFintResourceData(event.getData())) {
            log.info("Compatibility: Converting FintResource<FastlonnResource> to FastlonnResource ...");
            data = fintResourceCompatibility.convertResourceData(event.getData(), FastlonnResource.class);
        } else {
            data = objectMapper.convertValue(event.getData(), javaType);
        }
        data.forEach(resource -> {
            linker.mapLinks(resource);
            linker.resetSelfLinks(resource);
        });
        if (PersonalActions.valueOf(event.getAction()) == PersonalActions.UPDATE_FASTLONN) {
            if (event.getResponseStatus() == ResponseStatus.ACCEPTED || event.getResponseStatus() == ResponseStatus.CONFLICT) {
                List<CacheObject<FastlonnResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
                addCache(event.getOrgId(), cacheObjects);
                log.info("Added {} cache objects to cache for {}", cacheObjects.size(), event.getOrgId());
            } else {
                log.debug("Ignoring payload for {} with response status {}", event.getOrgId(), event.getResponseStatus());
            }
        } else {
            List<CacheObject<FastlonnResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
            updateCache(event.getOrgId(), cacheObjects);
            log.info("Updated cache for {} with {} cache objects", event.getOrgId(), cacheObjects.size());
        }
    }
}
