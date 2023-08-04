package dev.me.service2.service;

import dev.me.service2.entity.Bean;
import dev.me.service2.repository.BeanRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class Service2 {

    @Inject
    BeanRepository repository;

    public List<Bean> findAll() {
        return repository.findAll();
    }

    public Bean save(String name) {
        return repository.save(name);
    }

    public void update(UUID id, String name) {
        repository.updateById(id, name);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
