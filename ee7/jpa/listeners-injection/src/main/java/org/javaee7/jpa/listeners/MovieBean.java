package org.javaee7.jpa.listeners;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * @author Kuba Marchwicki
 */
@Stateless
public class MovieBean {
    @PersistenceContext
    private EntityManager em;

    public Movie getMovieByName(String name) {
        return em.createNamedQuery(Movie.FIND_BY_NAME, Movie.class)
            .setParameter("name", name)
            .getSingleResult();
    }

}
