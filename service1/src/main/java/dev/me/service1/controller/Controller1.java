package dev.me.service1.controller;

import dev.me.service1.service.Service1;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Path("resource1")
@Slf4j
public class Controller1 {

    @Inject
    Service1 service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        log.info("findAll()");
        return service.findAll();
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
