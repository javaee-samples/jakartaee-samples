package org.javaee7.jpa.ordercolumn.service.unidirectional;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.javaee7.jpa.ordercolumn.entity.unidirectional.Parent;

@Stateless
public class OrderColumnTesterService {

    @PersistenceContext
    EntityManager entityManager;

    public Parent save(Parent parent) {
        return entityManager.merge(parent);
    }

    public Parent getParentById(Long id) {
        return entityManager.find(Parent.class, id);
    }

}
