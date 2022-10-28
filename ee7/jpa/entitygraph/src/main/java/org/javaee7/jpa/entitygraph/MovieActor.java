package org.javaee7.jpa.entitygraph;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author Arun Gupta
 */
@Entity
@Table(name = "MOVIE_ACTORS_ENTITY_GRAPH")
public class MovieActor implements Serializable {

    private static final long serialVersionUID = 554523994022281173L;

    @Id
    private Integer id;

    @NotNull
    @Size(max = 50)
    private String actor;

    @OneToMany
    @JoinColumn(name = "ID")
    private Set<MovieActorAward> movieActorAwards;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MovieActor that = (MovieActor) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
