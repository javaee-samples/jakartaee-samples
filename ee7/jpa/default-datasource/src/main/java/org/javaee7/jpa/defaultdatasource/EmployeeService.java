package org.javaee7.jpa.defaultdatasource;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * @author Arun Gupta
 */
@Stateless
public class EmployeeService {

    @PersistenceContext
    EntityManager entityManager;

    public void persist(Employee employee) {
        entityManager.persist(employee);
    }

    public List<Employee> findAll() {
        return entityManager.createNamedQuery("Employee.findAll", Employee.class)
                 .getResultList();
    }
}
