package org.javaee7.jpa.storedprocedure;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Roberto Cortez
 */
@Stateless
public class MovieBean {
    @PersistenceContext
    private EntityManager em;

    public List<Movie> listMovies() {
        return em.createNamedQuery("Movie.findAll", Movie.class).getResultList();
    }

    public void executeStoredProcedure() {
        em.createNamedStoredProcedureQuery("top10Movies").execute();
    }
}
