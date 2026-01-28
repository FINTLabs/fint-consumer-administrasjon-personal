package no.fint.data;

import com.google.common.collect.ImmutableList;
import no.novari.fint.model.administrasjon.personal.Personalressurs;
import no.novari.fint.model.felles.Person;
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator;
import no.novari.fint.model.relation.FintResource;
import no.novari.fint.model.relation.Relation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalressursService {

    public List<FintResource<Personalressurs>> getAll() {
        Identifikator ansattnummer = new Identifikator();
        ansattnummer.setIdentifikatorverdi("10025");
        Personalressurs personalressurs = new Personalressurs();
        personalressurs.setAnsattnummer(ansattnummer);

        FintResource<Personalressurs> fintResource = FintResource.with(personalressurs).addRelations(
                new Relation.Builder().with(Personalressurs.Relasjonsnavn.PERSON).forType(Person.class).field("fodselsnummer").value("204194497763").build()
        );

        return ImmutableList.of(fintResource);
    }
}
