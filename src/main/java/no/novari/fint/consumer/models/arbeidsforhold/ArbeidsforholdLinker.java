package no.novari.fint.consumer.models.arbeidsforhold;

import no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource;
import no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResources;
import no.novari.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class ArbeidsforholdLinker extends FintLinker<ArbeidsforholdResource> {

    public ArbeidsforholdLinker() {
        super(ArbeidsforholdResource.class);
    }

    public void mapLinks(ArbeidsforholdResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public ArbeidsforholdResources toResources(Collection<ArbeidsforholdResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public ArbeidsforholdResources toResources(Stream<ArbeidsforholdResource> stream, int offset, int size, int totalItems) {
        ArbeidsforholdResources resources = new ArbeidsforholdResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(ArbeidsforholdResource arbeidsforhold) {
        return getAllSelfHrefs(arbeidsforhold).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(ArbeidsforholdResource arbeidsforhold) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(arbeidsforhold.getSystemId()) && !isEmpty(arbeidsforhold.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(arbeidsforhold.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(ArbeidsforholdResource arbeidsforhold) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(arbeidsforhold.getSystemId()) && !isEmpty(arbeidsforhold.getSystemId().getIdentifikatorverdi())) {
            builder.add(arbeidsforhold.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

