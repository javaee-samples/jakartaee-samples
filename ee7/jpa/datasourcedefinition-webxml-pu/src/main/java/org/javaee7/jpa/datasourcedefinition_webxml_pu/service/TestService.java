package org.javaee7.jpa.datasourcedefinition_webxml_pu.service;

import java.util.List;

import org.javaee7.jpa.datasourcedefinition_webxml_pu.entity.TestEntity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * This service does some JPA operations. The purpose of this entire test
 * is just to see whether the data source can be used so the actual operations
 * don't matter much.
 *
 * @author Arjan Tijms
 *
 */
@ApplicationScoped
public class TestService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveNewEntity() {

        TestEntity testEntity = new TestEntity();
        testEntity.setValue("mytest");

        entityManager.persist(testEntity);
    }

    @Transactional
    public List<TestEntity> getAllEntities() {
        return entityManager.createQuery("SELECT _testEntity FROM TestEntity _testEntity", TestEntity.class)
            .getResultList();
    }

}
