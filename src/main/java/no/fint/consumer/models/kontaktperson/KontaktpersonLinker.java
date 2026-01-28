package no.fint.consumer.models.kontaktperson;

import no.novari.fint.model.resource.felles.KontaktpersonResource;
import no.novari.fint.model.resource.felles.KontaktpersonResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class KontaktpersonLinker extends FintLinker<KontaktpersonResource> {

    public KontaktpersonLinker() {
        super(KontaktpersonResource.class);
    }

    public void mapLinks(KontaktpersonResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public KontaktpersonResources toResources(Collection<KontaktpersonResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public KontaktpersonResources toResources(Stream<KontaktpersonResource> stream, int offset, int size, int totalItems) {
        KontaktpersonResources resources = new KontaktpersonResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(KontaktpersonResource kontaktperson) {
        return getAllSelfHrefs(kontaktperson).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(KontaktpersonResource kontaktperson) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(kontaktperson.getSystemId()) && !isEmpty(kontaktperson.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(kontaktperson.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(KontaktpersonResource kontaktperson) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(kontaktperson.getSystemId()) && !isEmpty(kontaktperson.getSystemId().getIdentifikatorverdi())) {
            builder.add(kontaktperson.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

