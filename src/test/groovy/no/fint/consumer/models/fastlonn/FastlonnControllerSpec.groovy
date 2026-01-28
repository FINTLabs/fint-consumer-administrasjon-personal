package no.fint.consumer.models.fastlonn

import no.fint.consumer.utils.RestEndpoints
import no.novari.fint.model.administrasjon.kompleksedatatyper.Kontostreng
import no.novari.fint.model.administrasjon.personal.Fastlonn
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.kompleksedatatyper.KontostrengResource
import no.novari.fint.model.resource.administrasjon.personal.FastlonnResource
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
class FastlonnControllerSpec extends Specification {
    @LocalServerPort
    private int port

    @Autowired
    private TestRestTemplate restTemplate

    @Ignore("Jackson serializer issues with empty Kontostreng")
    def "POST Fastlonn"() {
        given:
        def fastlonn = new Fastlonn(
                prosent: 10000L,
                kontostreng: new Kontostreng(),
                periode: new Periode(start: new Date(), slutt: new Date()),
                attestert: new Date(),
                anvist: new Date(),
                opptjent: new Periode(start: new Date(), slutt: new Date()))

        HttpHeaders headers = new HttpHeaders()
        headers.add("x-org-id", "pwf.no")
        headers.add("x-client", "test")

        when:
        ResponseEntity<String> result = restTemplate.exchange("http://localhost:{port}{endpoint}", HttpMethod.POST, new HttpEntity<>(fastlonn, headers), String.class, port, RestEndpoints.FASTLONN)
        println(result)

        then:
        result.getStatusCode().is2xxSuccessful()
    }

    def "POST fastlonn.json and GET /status/"() {
        given:
        String content = IOUtils.toString(new ClassPathResource("fastlonn.json").getInputStream(), "UTF-8")
        HttpHeaders headers = new HttpHeaders()
        headers.add("x-org-id", "pwf.no")
        headers.add("x-client", "test")
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8)

        when:
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:{port}{endpoint}", new HttpEntity<>(content, headers), String.class, port, RestEndpoints.FASTLONN)
        System.out.println("result = " + result)

        then:
        result.getStatusCode().is2xxSuccessful()
        result.getHeaders().getLocation()

        when:
        def location = UriComponentsBuilder.fromUri(result.getHeaders().getLocation()).port(port).build().toUri()
        result = restTemplate.exchange(location, HttpMethod.GET, new HttpEntity<>(null, headers), String.class)
        println(result)

        then:
        result.getStatusCode() == HttpStatus.ACCEPTED

    }

    def "POST FastlonnResource"() {
        given:
        def kontostreng = new KontostrengResource()
        def fastlonn = new FastlonnResource(
                systemId: new Identifikator(identifikatorverdi: "AAA2222"),
                prosent: 10000L,
                kontostreng: kontostreng,
                periode: new Periode(start: new Date(), slutt: new Date()),
                attestert: new Date(),
                anvist: new Date(),
                opptjent: new Periode(start: new Date(), slutt: new Date()))
        kontostreng.addArt(Link.with('${administrasjon.kodeverk.art}/systemid/1'))
        fastlonn.addLonnsart(Link.with('${administrasjon.kodeverk.lonnsart}/systemid/1'))
        fastlonn.addArbeidsforhold(Link.with('${administrasjon.personal.arbeidsforhold}/ansattnummer/12345'))

        HttpHeaders headers = new HttpHeaders()
        headers.add("x-org-id", "pwf.no")
        headers.add("x-client", "test")

        when:
        ResponseEntity<String> result = restTemplate.exchange("http://localhost:{port}{endpoint}", HttpMethod.POST, new HttpEntity<>(fastlonn, headers), String.class, port, RestEndpoints.FASTLONN)
        println(result)

        then:
        result.getStatusCode().is2xxSuccessful()

    }

    def "GET /fastlonn"() {
        given:
        HttpHeaders headers = new HttpHeaders()
        headers.add("x-org-id", "mock.no")
        headers.add("x-client", "test")

        when:
        ResponseEntity<String> result = restTemplate.exchange("http://localhost:{port}{endpoint}", HttpMethod.GET, new HttpEntity<>(null, headers), String.class, port, RestEndpoints.FASTLONN)
        println(result)

        then:
        result.getStatusCode().is2xxSuccessful()
    }
}
