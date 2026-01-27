package no.fint.consumer.models.fravar;

import no.novari.fint.model.resource.administrasjon.personal.FravarResource;
import no.novari.fint.model.resource.administrasjon.personal.FravarResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class FravarLinker extends FintLinker<FravarResource> {

    public FravarLinker() {
        super(FravarResource.class);
    }

    public void mapLinks(FravarResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public FravarResources toResources(Collection<FravarResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public FravarResources toResources(Stream<FravarResource> stream, int offset, int size, int totalItems) {
        FravarResources resources = new FravarResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(FravarResource fravar) {
        return getAllSelfHrefs(fravar).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(FravarResource fravar) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(fravar.getKildesystemId()) && !isEmpty(fravar.getKildesystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(fravar.getKildesystemId().getIdentifikatorverdi(), "kildesystemid"));
        }
        if (!isNull(fravar.getSystemId()) && !isEmpty(fravar.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(fravar.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(FravarResource fravar) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(fravar.getKildesystemId()) && !isEmpty(fravar.getKildesystemId().getIdentifikatorverdi())) {
            builder.add(fravar.getKildesystemId().getIdentifikatorverdi().hashCode());
        }
        if (!isNull(fravar.getSystemId()) && !isEmpty(fravar.getSystemId().getIdentifikatorverdi())) {
            builder.add(fravar.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

