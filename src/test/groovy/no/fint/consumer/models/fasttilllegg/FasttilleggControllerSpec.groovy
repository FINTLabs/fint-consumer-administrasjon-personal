package no.fint.consumer.models.fasttilllegg

import no.fint.consumer.utils.RestEndpoints
import no.novari.fint.model.administrasjon.kompleksedatatyper.Kontostreng
import no.novari.fint.model.administrasjon.personal.Fasttillegg
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.kompleksedatatyper.KontostrengResource
import no.novari.fint.model.resource.administrasjon.personal.FasttilleggResource
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.ClassPathResource
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Ignore
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FasttilleggControllerSpec extends Specification {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Ignore("Jackson serializer issues with emtpy Kontostreng")
    def "POST Fasttillegg"() {
        given:
        def fasttillegg = new Fasttillegg(
                systemId: new Identifikator(identifikatorverdi: "AAA222"),
                belop: 10000L,
                kontostreng: new Kontostreng(),
                periode: new Periode(start: new Date(), slutt: new Date()),
                attestert: new Date(),
                anvist: new Date(),
                opptjent: new Periode(start: new Date(), slutt: new Date()))

        HttpHeaders headers = new HttpHeaders()
        headers.add("x-org-id", "pwf.no")
        headers.add("x-client", "test")

        when:
        ResponseEntity<String> result = restTemplate.exchange("http://localhost:{port}{endpoint}", HttpMethod.POST, new HttpEntity<>(fasttillegg, headers), String.class, port, RestEndpoints.FASTTILLEGG)
        println(result)

        then:
        result.getStatusCode().is2xxSuccessful()
    }

    def "POST fasttillegg.json and GET /status/"() {
        given:
        String content = IOUtils.toString(new ClassPathResource("fasttillegg.json").getInputStream(), "UTF-8")
        HttpHeaders headers = new HttpHeaders()
        headers.add("x-org-id", "pwf.no")
        headers.add("x-client", "test")
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8)
        def request = new HttpEntity<>(content, headers)

        when:
        println(request)
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:{port}{endpoint}", request, String.class, port, RestEndpoints.FASTTILLEGG)
        System.out.println("result = " + result)

        then:
        result.getStatusCode().is2xxSuccessful()
        result.getHeaders().getLocation()

        when:
        def location = UriComponentsBuilder.fromUri(result.getHeaders().getLocation()).port(port).build().toUri()
        println(location)
        result = restTemplate.exchange(location, HttpMethod.GET, new HttpEntity<>(null, headers), String.class)
        println(result)

        then:
        result.getStatusCode() == HttpStatus.ACCEPTED

    }

    def "POST FasttilleggResource()"() {
        given:
        def kontostreng = new KontostrengResource()
        def fasttillegg = new FasttilleggResource(
                systemId: new Identifikator(identifikatorverdi: "AAA2222"),
                belop: 10000L,
                kontostreng: kontostreng,
                periode: new Periode(start: new Date(), slutt: new Date()),
                attestert: new Date(),
                anvist: new Date(),
                opptjent: new Periode(start: new Date(), slutt: new Date()))
        kontostreng.addArt(Link.with('${administrasjon.kodeverk.art}/systemid/1'))
        fasttillegg.addLonnsart(Link.with('${administrasjon.kodeverk.lonnsart}/systemid/1'))
        fasttillegg.addArbeidsforhold(Link.with('${administrasjon.personal.arbeidsforhold}/ansattnummer/12345'))

        HttpHeaders headers = new HttpHeaders()
        headers.add("x-org-id", "pwf.no")
        headers.add("x-client", "test")

        when:
        ResponseEntity<String> result = restTemplate.exchange("http://localhost:{port}{endpoint}", HttpMethod.POST, new HttpEntity<>(fasttillegg, headers), String.class, port, RestEndpoints.FASTTILLEGG)
        println(result)

        then:
        result.getStatusCode().is2xxSuccessful()

    }

    def "GET /fasttillegg"() {
        given:
        HttpHeaders headers = new HttpHeaders()
        headers.add("x-org-id", "mock.no")
        headers.add("x-client", "test")

        when:
        ResponseEntity<String> result = restTemplate.exchange("http://localhost:{port}{endpoint}", HttpMethod.GET, new HttpEntity<>(null, headers), String.class, port, RestEndpoints.FASTTILLEGG)
        println(result)

        then:
        result.getStatusCode().is2xxSuccessful()
    }
}
