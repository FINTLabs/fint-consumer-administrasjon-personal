package no.fint.consumer.models.arbeidsforhold


import no.fint.consumer.utils.RestEndpoints
import no.fint.event.model.HeaderConstants
import no.novari.fint.model.resource.administrasjon.personal.ArbeidsforholdResource
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.util.stream.Stream

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ArbeidsforholdControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    ArbeidsforholdCacheService cacheService = Mock()

    def "GET last updated"() {
        when:
        def response = mockMvc.perform(get("${RestEndpoints.ARBEIDSFORHOLD}/last-updated")
                .header(HeaderConstants.ORG_ID, 'mock.no'))

        then:
        1 * cacheService.getLastUpdated(_ as String) >> 123L
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.lastUpdated').value('123'))
    }

    def 'GET results with pagination'() {
        when:
        def response = mockMvc.perform(
                get("${RestEndpoints.ARBEIDSFORHOLD}?size=1&offset=42")
                        .header(HeaderConstants.ORG_ID, 'mock.no')
                        .header(HeaderConstants.CLIENT, 'Spock'))

        then:
        1 * cacheService.streamSlice('mock.no', 42, 1) >> Stream.of(new ArbeidsforholdResource())
        1 * cacheService.getCacheSize('mock.no') >> 400
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.total_items').value( '400'))
                .andExpect(jsonPath('$.size').value( '1'))
                .andExpect(jsonPath('$.offset').value( '42'))
    }
}
