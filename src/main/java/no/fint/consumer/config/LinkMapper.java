package no.fint.consumer.config;

import no.fint.consumer.utils.RestEndpoints;
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
            .put("no.novari.fint.model.felles.kodeverk.iso.Landkode", "/felles/kodeverk/iso/landkode")
            .put("no.novari.fint.model.administrasjon.kodeverk.Aktivitet", "/administrasjon/kodeverk/aktivitet")
            .put("no.novari.fint.model.administrasjon.kodeverk.Anlegg", "/administrasjon/kodeverk/anlegg")
            .put("no.novari.fint.model.administrasjon.kodeverk.Ansvar", "/administrasjon/kodeverk/ansvar")
            .put("no.novari.fint.model.administrasjon.kodeverk.Arbeidsforholdstype", "/administrasjon/kodeverk/arbeidsforholdstype")
            .put("no.novari.fint.model.administrasjon.kodeverk.Art", "/administrasjon/kodeverk/art")
            .put("no.novari.fint.model.administrasjon.kodeverk.Diverse", "/administrasjon/kodeverk/diverse")
            .put("no.novari.fint.model.administrasjon.kodeverk.Formal", "/administrasjon/kodeverk/formal")
            .put("no.novari.fint.model.administrasjon.kodeverk.Funksjon", "/administrasjon/kodeverk/funksjon")
            .put("no.novari.fint.model.administrasjon.kodeverk.Kontrakt", "/administrasjon/kodeverk/kontrakt")
            .put("no.novari.fint.model.administrasjon.kodeverk.Lopenummer", "/administrasjon/kodeverk/lopenummer")
            .put("no.novari.fint.model.administrasjon.kodeverk.Objekt", "/administrasjon/kodeverk/objekt")
            .put("no.novari.fint.model.administrasjon.kodeverk.Prosjekt", "/administrasjon/kodeverk/prosjekt")
            .put("no.novari.fint.model.administrasjon.kodeverk.Ramme", "/administrasjon/kodeverk/ramme")
            .put("no.novari.fint.model.administrasjon.kodeverk.Stillingskode", "/administrasjon/kodeverk/stillingskode")
            .put("no.novari.fint.model.administrasjon.kodeverk.Uketimetall", "/administrasjon/kodeverk/uketimetall")
            .put("no.novari.fint.model.administrasjon.organisasjon.Arbeidslokasjon", "/administrasjon/organisasjon/arbeidslokasjon")
            .put("no.novari.fint.model.administrasjon.organisasjon.Organisasjonselement", "/administrasjon/organisasjon/organisasjonselement")
            .put("no.novari.fint.model.utdanning.elev.Undervisningsforhold", "/utdanning/elev/undervisningsforhold")
            .put("no.novari.fint.model.administrasjon.kodeverk.Lonnsart", "/administrasjon/kodeverk/lonnsart")
            .put("no.novari.fint.model.administrasjon.kodeverk.Fravarsgrunn", "/administrasjon/kodeverk/fravarsgrunn")
            .put("no.novari.fint.model.administrasjon.kodeverk.Fravarstype", "/administrasjon/kodeverk/fravarstype")
            .put("no.novari.fint.model.administrasjon.kodeverk.Prosjektart", "/administrasjon/kodeverk/prosjektart")
            .put("no.novari.fint.model.felles.kodeverk.Kommune", "/felles/kodeverk/kommune")
            .put("no.novari.fint.model.felles.kodeverk.iso.Kjonn", "/felles/kodeverk/iso/kjonn")
            .put("no.novari.fint.model.felles.kodeverk.iso.Sprak", "/felles/kodeverk/iso/sprak")
            .put("no.novari.fint.model.utdanning.larling.Larling", "/utdanning/larling/larling")
            .put("no.novari.fint.model.utdanning.elev.Elev", "/utdanning/elev/elev")
            .put("no.novari.fint.model.utdanning.ot.OtUngdom", "/utdanning/ot/otungdom")
            .put("no.novari.fint.model.administrasjon.kodeverk.Personalressurskategori", "/administrasjon/kodeverk/personalressurskategori")
            .put("no.novari.fint.model.administrasjon.fullmakt.Fullmakt", "/administrasjon/fullmakt/fullmakt")
            .put("no.novari.fint.model.utdanning.elev.Skoleressurs", "/utdanning/elev/skoleressurs")
            /* .put(TODO,TODO) */
            .build();
    }

}
