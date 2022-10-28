package org.javaee7.jpa.datasourcedefinition_applicationxml_pu.service;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.javaee7.jpa.datasourcedefinition_applicationxml_pu.entity.TestEntity;

/**
 * This service does some JPA operations. The purpose of this entire test
 * is just to see whether the data source can be used so the actual operations
 * don't matter much.
 * 
 * @author Arjan Tijms
 *
 */
@Stateless
public class TestService {

    @PersistenceContext
    private EntityManager entityManager;

    public void saveNewEntity() {

        TestEntity testEntity = new TestEntity();
        testEntity.setValue("mytest");

        entityManager.persist(testEntity);
    }

    public List<TestEntity> getAllEntities() {
        return entityManager.createQuery("SELECT _testEntity FROM TestEntity _testEntity", TestEntity.class)
            .getResultList();
    }

}
