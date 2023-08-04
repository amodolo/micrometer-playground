package dev.me.service2.repository;

import dev.me.service2.entity.Bean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@ApplicationScoped
public class BeanRepository {

    public List<Bean> findAll() {
        return execute(entityManager -> {
            TypedQuery<Bean> query = entityManager.createQuery("select bean from Bean bean", Bean.class);
            return query.getResultList();
        });
    }

    public Bean save(String name) {
        return execute(entityManager -> {
            entityManager.getTransaction().begin();
            Bean bean = Bean.builder().name(name).build();
            entityManager.persist(bean);
            entityManager.getTransaction().commit();
            return bean;
        });
    }

    public void updateById(UUID id, String name) {
        execute(entityManager -> {
            entityManager.getTransaction().begin();
            Bean bean = entityManager.find(Bean.class, id);
            bean.setName(name);
            entityManager.getTransaction().commit();
            return null;
        });
    }

    public void deleteById(UUID id) {
        execute(entityManager -> {
            entityManager.getTransaction().begin();
            Bean bean = entityManager.find(Bean.class, id);
            entityManager.remove(bean);
            entityManager.getTransaction().commit();
            return null;
        });
    }

    private <T> T execute(Function<EntityManager, T> consumer) {
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(null);
             EntityManager entityManager = factory.createEntityManager()) {
            return consumer.apply(entityManager);
        }
    }
}
