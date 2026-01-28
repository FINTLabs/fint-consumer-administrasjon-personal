package no.fint;

import com.google.common.collect.Lists;
import no.fint.data.ArbeidsforholdService;
import no.fint.data.PersonService;
import no.fint.data.PersonalressursService;
import no.fint.event.model.Event;
import no.fint.event.model.health.Health;
import no.fint.event.model.health.HealthStatus;
import no.fint.event.model.Status;
import no.fint.events.FintEvents;
import no.fint.events.FintEventsHealth;
import no.fint.events.annotations.FintEventListener;
import no.fint.events.queue.QueueType;
import no.novari.fint.model.relation.FintResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestListener {

    @Autowired
    private FintEvents fintEvents;

    @Autowired
    private FintEventsHealth fintEventsHealth;

    @Autowired
    private PersonalressursService personalressursService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ArbeidsforholdService arbeidsforholdService;

    @FintEventListener(type = QueueType.DOWNSTREAM)
    public void recieve(Event event) {
        if (event.isHealthCheck()) {
            event.addObject(new Health("test-provider", HealthStatus.RECEIVED_IN_PROVIDER_FROM_CONSUMER));
            event.addObject(new Health("test-provider", HealthStatus.SENT_FROM_PROVIDER_TO_ADAPTER));
            event.addObject(new Health("test-adapter", HealthStatus.APPLICATION_HEALTHY));
            event.addObject(new Health("test-provider", HealthStatus.RECEIVED_IN_PROVIDER_FROM_ADAPTER));
            event.addObject(new Health("test-provider", HealthStatus.SENT_FROM_PROVIDER_TO_CONSUMER));

            event.setStatus(Status.TEMP_UPSTREAM_QUEUE);
            fintEventsHealth.respondHealthCheck(event.getCorrId(), event);
        } else {
            List<FintResource> resources = Lists.newArrayList();
            switch (event.getAction()) {
                case "GET_ALL_PERSONALRESSURS":
                    resources.addAll(personalressursService.getAll());
                    break;
                case "GET_ALL_PERSON":
                    resources.addAll(personService.getAll());
                    break;
                case "GET_ALL_ARBEIDSFORHOLD":
                    resources.addAll(arbeidsforholdService.getAll());
                    break;
            }

            Event<FintResource> response = new Event<>(event);
            response.setStatus(Status.UPSTREAM_QUEUE);
            response.setData(resources);
            fintEvents.sendUpstream(event.getOrgId(), response);
        }
    }

}
