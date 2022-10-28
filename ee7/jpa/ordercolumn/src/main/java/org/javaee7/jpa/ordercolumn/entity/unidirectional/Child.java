package org.javaee7.jpa.ordercolumn.entity.unidirectional;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @SuppressWarnings("unused")
    private int dummy = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
