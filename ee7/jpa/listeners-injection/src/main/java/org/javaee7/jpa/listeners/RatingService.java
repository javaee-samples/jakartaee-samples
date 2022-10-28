package org.javaee7.jpa.listeners;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * @author Kuba Marchwicki
 */

@Stateless
public class RatingService {
    @PersistenceContext
    private EntityManager em;

    public Integer movieRating(String name) {
        return em.createNamedQuery(Rating.FIND_BY_NAME, Rating.class)
            .setParameter("name", name)
            .getSingleResult()
            .getRating();
    }

}
