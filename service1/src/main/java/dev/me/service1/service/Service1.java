package dev.me.service1.service;

import dev.me.service1.model.ResourceCreatedResponse;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.UUID;

@ApplicationScoped
@Slf4j
public class Service1 {
    @Inject
    KafkaClientService kafkaClientService;

    private Client client;

    @PostConstruct
    void initClient() {
        client = ClientBuilder.newClient();
    }

    @PreDestroy
    void destroyClient() {
        client.close();
    }

    public Response findAll() {
        String url = getServiceBaseURL();
        log.info("GET {}", url);
        return client.target(url)
            .request()
            .get();
    }

    public Response save(String name) {
        String url = getServiceBaseURL();
        log.info("POST {}", url);

        Response response = client.target(url)
            .request()
            .post(Entity.text(name));

        if (response.getStatus() == 200) {
            ResourceCreatedResponse entity = response.readEntity(ResourceCreatedResponse.class);
            kafkaClientService.send("bean-created", entity.id().toString(), entity.name());
            return Response.created(URI.create(String.format("%s/%s", url, entity.id()))).entity(entity).build();
        } else {
            return response;
        }
    }

    public Response update(UUID id, String name) {
        String url = getServiceBaseURL();
        log.info("PUT {}", url);
        Response response = client.target(String.format("%s/%s", url, id.toString()))
            .request()
            .put(Entity.text(name));

        if (response.getStatus() == 201) {
            kafkaClientService.send("bean-updated", id.toString(), name);
        }

        return response;
    }

    public Response deleteById(UUID id) {
        String url = getServiceBaseURL();
        log.info("DELETE {}", url);
        Response response = client.target(String.format("%s/%s", url, id))
            .request()
            .delete();

        if (response.getStatus() == 201) {
            kafkaClientService.send("bean-deleted", id.toString(), null);
        }

        return response;
    }

    String getServiceBaseURL() {
        String url = System.getenv("SERVICE2_URL");
        return url != null ? url : "http://localhost:8081/service2/api/resource2";
    }
}
