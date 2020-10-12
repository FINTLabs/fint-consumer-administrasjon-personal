package no.fint.consumer.config;

import no.fint.consumer.utils.RestEndpoints;
import java.util.Map;
import com.google.common.collect.ImmutableMap;

import no.fint.model.administrasjon.personal.*;
import no.fint.model.felles.Kontaktperson;
import no.fint.model.felles.Person;

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
            .put("no.fint.model.felles.kodeverk.iso.Landkode", "/felles/kodeverk/iso/landkode")
            .put("no.fint.model.administrasjon.kodeverk.Ansvar", "/administrasjon/kodeverk/ansvar")
            .put("no.fint.model.administrasjon.kodeverk.Arbeidsforholdstype", "/administrasjon/kodeverk/arbeidsforholdstype")
            .put("no.fint.model.administrasjon.kodeverk.Art", "/administrasjon/kodeverk/art")
            .put("no.fint.model.administrasjon.kodeverk.Funksjon", "/administrasjon/kodeverk/funksjon")
            .put("no.fint.model.administrasjon.kodeverk.Stillingskode", "/administrasjon/kodeverk/stillingskode")
            .put("no.fint.model.administrasjon.kodeverk.Uketimetall", "/administrasjon/kodeverk/uketimetall")
            .put("no.fint.model.administrasjon.organisasjon.Organisasjonselement", "/administrasjon/organisasjon/organisasjonselement")
            .put("no.fint.model.utdanning.elev.Undervisningsforhold", "/utdanning/elev/undervisningsforhold")
            .put("no.fint.model.administrasjon.kodeverk.Lonnsart", "/administrasjon/kodeverk/lonnsart")
            .put("no.fint.model.administrasjon.kodeverk.Fravarsgrunn", "/administrasjon/kodeverk/fravarsgrunn")
            .put("no.fint.model.administrasjon.kodeverk.Fravarstype", "/administrasjon/kodeverk/fravarstype")
            .put("no.fint.model.administrasjon.kodeverk.Aktivitet", "/administrasjon/kodeverk/aktivitet")
            .put("no.fint.model.administrasjon.kodeverk.Anlegg", "/administrasjon/kodeverk/anlegg")
            .put("no.fint.model.administrasjon.kodeverk.Diverse", "/administrasjon/kodeverk/diverse")
            .put("no.fint.model.administrasjon.kodeverk.Kontrakt", "/administrasjon/kodeverk/kontrakt")
            .put("no.fint.model.administrasjon.kodeverk.Lopenummer", "/administrasjon/kodeverk/lopenummer")
            .put("no.fint.model.administrasjon.kodeverk.Objekt", "/administrasjon/kodeverk/objekt")
            .put("no.fint.model.administrasjon.kodeverk.Prosjekt", "/administrasjon/kodeverk/prosjekt")
            .put("no.fint.model.administrasjon.kodeverk.Ramme", "/administrasjon/kodeverk/ramme")
            .put("no.fint.model.felles.kodeverk.iso.Kjonn", "/felles/kodeverk/iso/kjonn")
            .put("no.fint.model.felles.kodeverk.iso.Sprak", "/felles/kodeverk/iso/sprak")
            .put("no.fint.model.utdanning.elev.Elev", "/utdanning/elev/elev")
            .put("no.fint.model.administrasjon.kodeverk.Personalressurskategori", "/administrasjon/kodeverk/personalressurskategori")
            .put("no.fint.model.administrasjon.fullmakt.Fullmakt", "/administrasjon/fullmakt/fullmakt")
            .put("no.fint.model.utdanning.elev.Skoleressurs", "/utdanning/elev/skoleressurs")
            /* .put(TODO,TODO) */
            .build();
    }

}
