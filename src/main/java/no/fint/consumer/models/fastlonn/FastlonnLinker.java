package no.fint.consumer.models.fastlonn;

import no.novari.fint.model.resource.administrasjon.personal.FastlonnResource;
import no.novari.fint.model.resource.administrasjon.personal.FastlonnResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class FastlonnLinker extends FintLinker<FastlonnResource> {

    public FastlonnLinker() {
        super(FastlonnResource.class);
    }

    public void mapLinks(FastlonnResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public FastlonnResources toResources(Collection<FastlonnResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public FastlonnResources toResources(Stream<FastlonnResource> stream, int offset, int size, int totalItems) {
        FastlonnResources resources = new FastlonnResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(FastlonnResource fastlonn) {
        return getAllSelfHrefs(fastlonn).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(FastlonnResource fastlonn) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(fastlonn.getKildesystemId()) && !isEmpty(fastlonn.getKildesystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(fastlonn.getKildesystemId().getIdentifikatorverdi(), "kildesystemid"));
        }
        if (!isNull(fastlonn.getSystemId()) && !isEmpty(fastlonn.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(fastlonn.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(FastlonnResource fastlonn) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(fastlonn.getKildesystemId()) && !isEmpty(fastlonn.getKildesystemId().getIdentifikatorverdi())) {
            builder.add(fastlonn.getKildesystemId().getIdentifikatorverdi().hashCode());
        }
        if (!isNull(fastlonn.getSystemId()) && !isEmpty(fastlonn.getSystemId().getIdentifikatorverdi())) {
            builder.add(fastlonn.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

