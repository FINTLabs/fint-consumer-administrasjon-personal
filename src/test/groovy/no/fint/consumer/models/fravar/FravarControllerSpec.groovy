package no.fint.consumer.models.fravar

import no.fint.consumer.utils.RestEndpoints
import no.novari.fint.model.administrasjon.kodeverk.Fravarsgrunn
import no.novari.fint.model.administrasjon.kodeverk.Fravarstype
import no.novari.fint.model.administrasjon.personal.Arbeidsforhold
import no.novari.fint.model.administrasjon.personal.Fravar
import no.novari.fint.model.felles.kompleksedatatyper.Identifikator
import no.novari.fint.model.felles.kompleksedatatyper.Periode
import no.novari.fint.model.resource.Link
import no.novari.fint.model.resource.administrasjon.personal.FravarResource
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.ClassPathResource
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.util.concurrent.TimeUnit

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FravarControllerSpec extends Specification {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    def "POST Fravar"() {
        given:
        def fravar = new Fravar(systemId: new Identifikator(identifikatorverdi: "AAA"), periode: new Periode(start: new Date()), prosent: 10000)

        HttpHeaders headers = new HttpHeaders()
        headers.add("x-org-id", "mock.no")
        headers.add("x-client", "test")

        when:
        ResponseEntity<String> result = restTemplate.exchange("http://localhost:{port}{endpoint}", HttpMethod.POST, new HttpEntity<>(fravar, headers), String.class, port, RestEndpoints.FRAVAR)
        println(result)

        then:
        result.getStatusCode().is(HttpStatus.ACCEPTED)

        when:
        ResponseEntity<String> status = restTemplate.exchange("http://localhost:{port}{endpoint}", HttpMethod.GET, new HttpEntity<>(null, headers), String.class, port, result.headers.getLocation().getPath())
        println(status)

        then:
        result.getStatusCode().is(HttpStatus.ACCEPTED)
    }

    def "POST fravar.json"() {
        given:
        String content = IOUtils.toString(new ClassPathResource("fravar.json").getInputStream(), "UTF-8")
        HttpHeaders headers = new HttpHeaders()
        headers.add("x-org-id", "mock.no")
        headers.add("x-client", "test")
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8)

        when:
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:{port}{endpoint}", new HttpEntity<>(content, headers), String.class, port, RestEndpoints.FRAVAR)
        System.out.println("result = " + result)

        then:
        result.getStatusCode().is(HttpStatus.ACCEPTED)

        when:
        ResponseEntity<String> status = restTemplate.exchange("http://localhost:{port}{endpoint}", HttpMethod.GET, new HttpEntity<>(null, headers), String.class, port, result.headers.getLocation().getPath())
        println(status)

        then:
        result.getStatusCode().is(HttpStatus.ACCEPTED)
    }

    def "POST FravarResource"() {
        given:
        def fravar = new FravarResource(systemId: new Identifikator(identifikatorverdi: "AAA2222"), prosent: 10000, periode: new Periode(start: new Date(System.currentTimeMillis()-TimeUnit.DAYS.toMillis(1)), slutt: new Date()))
        fravar.addArbeidsforhold(Link.with(Arbeidsforhold,"ansattnummer", "12345"))
        fravar.addFravarsgrunn(Link.with(Fravarsgrunn, "systemid", "1"))
        fravar.addFravarstype(Link.with(Fravarstype, "systemid", "1"))

        HttpHeaders headers = new HttpHeaders()
        headers.add("x-org-id", "mock.no")
        headers.add("x-client", "test")

        when:
        ResponseEntity<String> result = restTemplate.exchange("http://localhost:{port}{endpoint}", HttpMethod.POST, new HttpEntity<>(fravar, headers), String.class, port, RestEndpoints.FRAVAR)
        println(result)

        then:
        result.getStatusCode().is(HttpStatus.ACCEPTED)

        when:
        ResponseEntity<String> status = restTemplate.exchange("http://localhost:{port}{endpoint}", HttpMethod.GET, new HttpEntity<>(null, headers), String.class, port, result.headers.getLocation().getPath())
        println(status)

        then:
        result.getStatusCode().is(HttpStatus.ACCEPTED)

    }

    def "GET /fravar"() {
        given:
        HttpHeaders headers = new HttpHeaders()
        headers.add("x-org-id", "mock.no")
        headers.add("x-client", "test")

        when:
        ResponseEntity<String> result = restTemplate.exchange("http://localhost:{port}{endpoint}", HttpMethod.GET, new HttpEntity<>(null, headers), String.class, port, RestEndpoints.FRAVAR)
        println(result)

        then:
        result.getStatusCode().is2xxSuccessful()
    }
}
