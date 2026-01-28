package no.fint.consumer.models.fastlonn

import com.fasterxml.jackson.databind.ObjectMapper
import no.fint.cache.FintCacheManager
import no.fint.consumer.config.ConsumerProps
import no.fint.consumer.event.ConsumerEventUtil
import no.fint.event.model.Event
import no.novari.fint.model.administrasjon.kompleksedatatyper.Kontostreng
import no.novari.fint.model.administrasjon.personal.Fastlonn
import no.novari.fint.model.administrasjon.personal.PersonalActions
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.relation.FintResource
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.kompleksedatatyper.KontostrengResource
import no.novari.fint.model.resource.administrasjon.personal.FastlonnResource
import no.fint.relations.FintResourceCompatibility
import no.fint.relations.config.FintRelationsProps
import no.fint.relations.internal.FintLinkMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import spock.lang.Specification

@SpringBootTest(classes = [FastlonnCacheService, FintCacheManager, ConsumerProps, ObjectMapper, FintResourceCompatibility, FastlonnLinker, FintLinkMapper, FintRelationsProps], properties = ["fint.events.orgIds=mock.no"])
class FastlonnCacheServiceSpec extends Specification {

    @MockBean
    ConsumerEventUtil consumerEventUtil

    @Autowired
    def fastlonnCacheService

    def "Update cache using FastlonnResource"() {
        given:
        def kontostreng = new KontostrengResource()
        kontostreng.addArt(Link.with("/administrasjon/kodeverk/art/systemid/1"))
        kontostreng.addAnsvar(Link.with("/administrasjon/kodeverk/ansvar/systemid/2"))
        kontostreng.addFunksjon(Link.with("/administrasjon/kodeverk/funksjon/systemid/3"))
        def fastlonn = new FastlonnResource(
                systemId: new Identifikator(identifikatorverdi: "ABC123"),
                attestert: new Date(System.currentTimeMillis()-10000),
                anvist: new Date(),
                periode: new Periode(start: new Date()),
                prosent: 10000,
                beskrivelse: "Test",
                kontostreng: kontostreng)
        fastlonn.addLonnsart(Link.with("/administrasjon/kodeverk/lonnsart/systemid/4"))
        fastlonn.addArbeidsforhold(Link.with("/administrasjon/personal/arbeidsforhold/systemid/1234"))

        when:
        def event = new Event(data: [fastlonn], orgId: "mock.no", action: PersonalActions.GET_ALL_FASTLONN, client: "Spock")
        fastlonnCacheService.onAction(event)

        then:
        fastlonnCacheService.hasItems()
        fastlonnCacheService.getAll("mock.no")[0].systemId.identifikatorverdi == "ABC123"
    }

    def "Update cache using FintResource<FastlonnResource>"() {
        given:
        def kontostreng = new KontostrengResource()
        kontostreng.addArt(Link.with("/administrasjon/kodeverk/art/systemid/1"))
        kontostreng.addAnsvar(Link.with("/administrasjon/kodeverk/ansvar/systemid/2"))
        kontostreng.addFunksjon(Link.with("/administrasjon/kodeverk/funksjon/systemid/3"))
        def fastlonn = new FastlonnResource(
                systemId: new Identifikator(identifikatorverdi: "ABC123"),
                attestert: new Date(System.currentTimeMillis()-10000),
                anvist: new Date(),
                periode: new Periode(start: new Date()),
                prosent: 10000,
                beskrivelse: "Test",
                kontostreng: kontostreng)
        fastlonn.addLonnsart(Link.with("/administrasjon/kodeverk/lonnsart/systemid/4"))
        fastlonn.addArbeidsforhold(Link.with("/administrasjon/personal/arbeidsforhold/systemid/1234"))

        when:
        def event = new Event(data: [FintResource.with(fastlonn)], orgId: "mock.no", action: PersonalActions.GET_ALL_FASTLONN, client: "Spock")
        fastlonnCacheService.onAction(event)

        then:
        fastlonnCacheService.hasItems()
        fastlonnCacheService.getAll("mock.no")[0].systemId.identifikatorverdi == "ABC123"
    }

    def "Update cache using FintResource<Fastlonn>"() {
        given:
        def fastlonn = new Fastlonn(
                systemId: new Identifikator(identifikatorverdi: "ABC123"),
                attestert: new Date(System.currentTimeMillis()-10000),
                anvist: new Date(),
                periode: new Periode(start: new Date()),
                prosent: 10000, beskrivelse: "Test", kontostreng: new Kontostreng())

        when:
        def event = new Event(data: [FintResource.with(fastlonn)], orgId: "mock.no", action: PersonalActions.GET_ALL_FASTLONN, client: "Spock")
        fastlonnCacheService.onAction(event)

        then:
        fastlonnCacheService.hasItems()
        fastlonnCacheService.getAll("mock.no")[0].systemId.identifikatorverdi == "ABC123"

    }
}
