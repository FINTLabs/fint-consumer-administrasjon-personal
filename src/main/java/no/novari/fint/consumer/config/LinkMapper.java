package no.novari.fint.consumer.config;

import no.novari.fint.consumer.utils.RestEndpoints;
import java.util.Map;
import com.google.common.collect.ImmutableMap;
import no.novari.fint.model.administrasjon.personal.Arbeidsforhold;
import no.novari.fint.model.administrasjon.personal.Fastlonn;
import no.novari.fint.model.administrasjon.personal.Fasttillegg;
import no.novari.fint.model.administrasjon.personal.Fravar;
import no.novari.fint.model.felles.Kontaktperson;
import no.novari.fint.model.felles.Person;
import no.novari.fint.model.administrasjon.personal.Personalressurs;
import no.novari.fint.model.administrasjon.personal.Variabellonn;

public class LinkMapper {

    public static Map<String, String> linkMapper(String contextPath) {
        return ImmutableMap.<String,String>builder()
            .put(Arbeidsforhold.class.getName(), contextPath + RestEndpoints.ARBEIDSFORHOLD)
            .put(Fastlonn.class.getName(), contextPath + RestEndpoints.FASTLONN)
            .put(Fasttillegg.class.getName(), contextPath + RestEndpoints.FASTTILLEGG)
            .put(Fravar.class.getName(), contextPath + RestEndpoints.FRAVAR)
            .put(Kontaktperson.class.getName(), contextPath + RestEndpoints.KONTAKTPERSON)
            .put(Person.class.getName(), contextPath + RestEndpoints.PERSON)
            .put(Personalressurs.class.getName(), contextPath + RestEndpoints.PERSONALRESSURS)
            .put(Variabellonn.class.getName(), contextPath + RestEndpoints.VARIABELLONN)
            .put("no.novari.fint.model.felles.kodeverk.iso.Landkode", "/model/felles/kodeverk/iso/landkode")
            .put("no.novari.fint.model.administrasjon.kodeverk.Aktivitet", "/model/administrasjon/kodeverk/aktivitet")
            .put("no.novari.fint.model.administrasjon.kodeverk.Anlegg", "/model/administrasjon/kodeverk/anlegg")
            .put("no.novari.fint.model.administrasjon.kodeverk.Ansvar", "/model/administrasjon/kodeverk/ansvar")
            .put("no.novari.fint.model.administrasjon.kodeverk.Arbeidsforholdstype", "/model/administrasjon/kodeverk/arbeidsforholdstype")
            .put("no.novari.fint.model.administrasjon.kodeverk.Art", "/model/administrasjon/kodeverk/art")
            .put("no.novari.fint.model.administrasjon.kodeverk.Diverse", "/model/administrasjon/kodeverk/diverse")
            .put("no.novari.fint.model.administrasjon.kodeverk.Formal", "/model/administrasjon/kodeverk/formal")
            .put("no.novari.fint.model.administrasjon.kodeverk.Funksjon", "/model/administrasjon/kodeverk/funksjon")
            .put("no.novari.fint.model.administrasjon.kodeverk.Kontrakt", "/model/administrasjon/kodeverk/kontrakt")
            .put("no.novari.fint.model.administrasjon.kodeverk.Lopenummer", "/model/administrasjon/kodeverk/lopenummer")
            .put("no.novari.fint.model.administrasjon.kodeverk.Objekt", "/model/administrasjon/kodeverk/objekt")
            .put("no.novari.fint.model.administrasjon.kodeverk.Prosjekt", "/model/administrasjon/kodeverk/prosjekt")
            .put("no.novari.fint.model.administrasjon.kodeverk.Ramme", "/model/administrasjon/kodeverk/ramme")
            .put("no.novari.fint.model.administrasjon.kodeverk.Stillingskode", "/model/administrasjon/kodeverk/stillingskode")
            .put("no.novari.fint.model.administrasjon.kodeverk.Uketimetall", "/model/administrasjon/kodeverk/uketimetall")
            .put("no.novari.fint.model.administrasjon.organisasjon.Arbeidslokasjon", "/model/administrasjon/organisasjon/arbeidslokasjon")
            .put("no.novari.fint.model.administrasjon.organisasjon.Organisasjonselement", "/model/administrasjon/organisasjon/organisasjonselement")
            .put("no.novari.fint.model.utdanning.elev.Undervisningsforhold", "/model/utdanning/elev/undervisningsforhold")
            .put("no.novari.fint.model.administrasjon.kodeverk.Lonnsart", "/model/administrasjon/kodeverk/lonnsart")
            .put("no.novari.fint.model.administrasjon.kodeverk.Fravarsgrunn", "/model/administrasjon/kodeverk/fravarsgrunn")
            .put("no.novari.fint.model.administrasjon.kodeverk.Fravarstype", "/model/administrasjon/kodeverk/fravarstype")
            .put("no.novari.fint.model.administrasjon.kodeverk.Prosjektart", "/model/administrasjon/kodeverk/prosjektart")
            .put("no.novari.fint.model.felles.kodeverk.Kommune", "/model/felles/kodeverk/kommune")
            .put("no.novari.fint.model.felles.kodeverk.iso.Kjonn", "/model/felles/kodeverk/iso/kjonn")
            .put("no.novari.fint.model.felles.kodeverk.iso.Sprak", "/model/felles/kodeverk/iso/sprak")
            .put("no.novari.fint.model.utdanning.larling.Larling", "/model/utdanning/larling/larling")
            .put("no.novari.fint.model.utdanning.elev.Elev", "/model/utdanning/elev/elev")
            .put("no.novari.fint.model.utdanning.ot.OtUngdom", "/model/utdanning/ot/otungdom")
            .put("no.novari.fint.model.administrasjon.kodeverk.Personalressurskategori", "/model/administrasjon/kodeverk/personalressurskategori")
            .put("no.novari.fint.model.administrasjon.fullmakt.Fullmakt", "/model/administrasjon/fullmakt/fullmakt")
            .put("no.novari.fint.model.utdanning.elev.Skoleressurs", "/model/utdanning/elev/skoleressurs")
            /* .put(TODO,TODO) */
            .build();
    }

}
