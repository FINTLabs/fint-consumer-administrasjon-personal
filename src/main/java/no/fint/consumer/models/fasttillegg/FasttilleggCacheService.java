package no.fint.consumer.models.fasttillegg;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

import no.fint.cache.CacheService;
import no.fint.consumer.config.Constants;
import no.fint.consumer.config.ConsumerProps;
import no.fint.consumer.event.ConsumerEventUtil;
import no.fint.event.model.Event;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.relations.FintResourceCompatibility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import no.fint.model.administrasjon.personal.Fasttillegg;
import no.fint.model.resource.administrasjon.personal.FasttilleggResource;
import no.fint.model.administrasjon.personal.PersonalActions;

@Slf4j
@Service
public class FasttilleggCacheService extends CacheService<FasttilleggResource> {

    public static final String MODEL = Fasttillegg.class.getSimpleName().toLowerCase();

    @Value("${fint.consumer.compatibility.fintresource:true}")
    private boolean checkFintResourceCompatibility;

    @Autowired
    private FintResourceCompatibility fintResourceCompatibility;

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    @Autowired
    private FasttilleggLinker linker;

    private JavaType javaType;

    private ObjectMapper objectMapper;

    public FasttilleggCacheService() {
        super(MODEL, PersonalActions.GET_ALL_FASTTILLEGG, PersonalActions.UPDATE_FASTTILLEGG);
        objectMapper = new ObjectMapper();
        javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, FasttilleggResource.class);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @PostConstruct
    public void init() {
        Arrays.stream(props.getOrgs()).forEach(this::createCache);
    }

    @Scheduled(initialDelayString = ConsumerProps.CACHE_INITIALDELAY_FASTTILLEGG, fixedRateString = ConsumerProps.CACHE_FIXEDRATE_FASTTILLEGG)
    public void populateCacheAll() {
        Arrays.stream(props.getOrgs()).forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
		flush(orgId);
		populateCache(orgId);
	}

    private void populateCache(String orgId) {
		log.info("Populating Fasttillegg cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, PersonalActions.GET_ALL_FASTTILLEGG, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }


    public Optional<FasttilleggResource> getFasttilleggBySystemId(String orgId, String systemId) {
        return getOne(orgId, (resource) -> Optional
                .ofNullable(resource)
                .map(FasttilleggResource::getSystemId)
                .map(Identifikator::getIdentifikatorverdi)
                .map(_id -> _id.equals(systemId))
                .orElse(false));
    }


	@Override
    public void onAction(Event event) {
        List<FasttilleggResource> data;
        if (checkFintResourceCompatibility && fintResourceCompatibility.isFintResourceData(event.getData())) {
            log.info("Compatibility: Converting FintResource<FasttilleggResource> to FasttilleggResource ...");
            data = fintResourceCompatibility.convertResourceData(event.getData(), FasttilleggResource.class);
        } else {
            data = objectMapper.convertValue(event.getData(), javaType);
        }
        data.forEach(linker::mapLinks);
        if (PersonalActions.valueOf(event.getAction()) == PersonalActions.UPDATE_FASTTILLEGG) {
            add(event.getOrgId(), data);
            log.info("Added {} elements to cache for {}", data.size(), event.getOrgId());
        } else {
            update(event.getOrgId(), data);
            log.info("Updated cache for {} with {} elements", event.getOrgId(), data.size());
        }
    }
}