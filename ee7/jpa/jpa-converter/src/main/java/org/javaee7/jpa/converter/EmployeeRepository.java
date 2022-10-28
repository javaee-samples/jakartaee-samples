package org.javaee7.jpa.converter;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * @author Arun Gupta
 */
@Stateless
public class EmployeeRepository {

    @PersistenceContext
    private EntityManager em;

    public void persist(Employee e) {
        em.persist(e);
    }

    public List<Employee> all() {
        return em.createNamedQuery("Employee.findAll", Employee.class).getResultList();
    }
}
