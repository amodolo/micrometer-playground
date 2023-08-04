package dev.me.service1.controller;

import dev.me.service1.service.Service1;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("resource1")
@Slf4j
public class Controller1 {

    @Inject
    Service1 service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String, Object>> findAll() {
        log.info("findAll()");
        List<Map<String, Object>> response = service.findAll();
        log.info("there are {} resources", response.size());
        return response;
    }
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String name) {
        log.info("create(name=\"{}\")", name);
        return service.save(name);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response update(@PathParam("id") UUID id, String name) {
        log.info("update(id=\"{}\", name=\"{}\")", id, name);
        return service.update(id, name);
    }

    @DELETE
    @Path("{id}")
    public Response deleteById(@PathParam("id") UUID id) {
        log.info("deleteById(id=\"{}\")", id);
        return service.deleteById(id);
    }
}
