package org.javaee7.jpa.extended.pc;

import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import java.io.Serializable;
import java.util.List;

/**
 * @author Kuba Marchwicki
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
public class CharactersBean implements Serializable {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    public void save(Character e) {
        em.persist(e);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void commitChanges() {

    }

    public List<Character> get() {
        return em.createNamedQuery(Character.FIND_ALL, Character.class).getResultList();
    }

}
