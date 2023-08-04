package dev.me.service1.service;

import dev.me.service1.model.ResourceCreatedResponse;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class Service1 {
    private static final String BASE_URL = "http://localhost:8081/service2/api/resource2";

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

    public List<Map<String, Object>> findAll() {
        GenericType<List<Map<String, Object>>> responseType = new GenericType<>() {
        };
        return client.target(BASE_URL)
                .request()
                .get(responseType);
    }

    public Response save(String name) {

        Response response = client.target(BASE_URL)
                .request()
                .post(Entity.text(name));

        if (response.getStatus() == 200) {
            ResourceCreatedResponse entity = response.readEntity(ResourceCreatedResponse.class);
            kafkaClientService.send("bean-created", entity.id().toString(), entity.name());
            return Response.created(URI.create(String.format("%s/%s", BASE_URL, entity.id()))).entity(entity).build();
        } else {
            return response;
        }
    }

    public Response update(UUID id, String name) {
        Response response = client.target(String.format("%s/%s", BASE_URL, id.toString()))
                .request()
                .put(Entity.text(name));

        if (response.getStatus() == 201) {
            kafkaClientService.send("bean-updated", id.toString(), name);
        }

        return response;
    }

    public Response deleteById(UUID id) {
        Response response = client.target(String.format("%s/%s", BASE_URL, id))
                .request()
                .delete();

        if (response.getStatus() == 201) {
            kafkaClientService.send("bean-deleted", id.toString(), null);
        }

        return response;
    }
}
