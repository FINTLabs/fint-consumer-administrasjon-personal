package no.fint.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator;
import no.novari.fint.model.felles.Person;
import no.novari.fint.model.relation.FintResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    public List<FintResource<Person>> getAll() {
        Identifikator fodselsnummer = new Identifikator();
        fodselsnummer.setIdentifikatorverdi("204194497763");
        Person person = new Person();
        person.setFodselsnummer(fodselsnummer);

        FintResource<Person> fintResource = FintResource.with(person);
        return ImmutableList.of(fintResource);
    }
}
