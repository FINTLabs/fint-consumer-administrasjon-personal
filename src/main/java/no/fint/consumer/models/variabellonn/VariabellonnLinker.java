package no.fint.consumer.models.variabellonn;

import no.novari.fint.model.resource.administrasjon.personal.VariabellonnResource;
import no.novari.fint.model.resource.administrasjon.personal.VariabellonnResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class VariabellonnLinker extends FintLinker<VariabellonnResource> {

    public VariabellonnLinker() {
        super(VariabellonnResource.class);
    }

    public void mapLinks(VariabellonnResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public VariabellonnResources toResources(Collection<VariabellonnResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public VariabellonnResources toResources(Stream<VariabellonnResource> stream, int offset, int size, int totalItems) {
        VariabellonnResources resources = new VariabellonnResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(VariabellonnResource variabellonn) {
        return getAllSelfHrefs(variabellonn).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(VariabellonnResource variabellonn) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(variabellonn.getKildesystemId()) && !isEmpty(variabellonn.getKildesystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(variabellonn.getKildesystemId().getIdentifikatorverdi(), "kildesystemid"));
        }
        if (!isNull(variabellonn.getSystemId()) && !isEmpty(variabellonn.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(variabellonn.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(VariabellonnResource variabellonn) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(variabellonn.getKildesystemId()) && !isEmpty(variabellonn.getKildesystemId().getIdentifikatorverdi())) {
            builder.add(variabellonn.getKildesystemId().getIdentifikatorverdi().hashCode());
        }
        if (!isNull(variabellonn.getSystemId()) && !isEmpty(variabellonn.getSystemId().getIdentifikatorverdi())) {
            builder.add(variabellonn.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

