package dev.me.service2.controller;

import dev.me.service2.entity.Bean;
import dev.me.service2.service.Service2;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Path("resource2")
@Slf4j
public class Controller2 {

    @Inject
    Service2 service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bean> findAll() {
        log.info("findAll()");
        return service.findAll();
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Bean create(String name) {
        log.info("create(name=\"{}\")", name);
        return service.save(name);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void update(@PathParam("id") UUID id, String name) {
        log.info("update(id=\"{}\", name=\"{}\")", id, name);
        service.update(id, name);
    }

    @DELETE
    @Path("{id}")
    public void deleteById(@PathParam("id") UUID id) {
        log.info("deleteById(id=\"{}\")", id);
        service.deleteById(id);
    }
}
