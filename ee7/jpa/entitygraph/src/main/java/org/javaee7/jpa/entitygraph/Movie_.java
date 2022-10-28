package org.javaee7.jpa.entitygraph;

import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * @author Roberto Cortez
 */
@StaticMetamodel(Movie.class)
public abstract class Movie_ {
    public static volatile SingularAttribute<Movie, Integer> id;
    public static volatile SetAttribute<Movie, MovieAward> movieAwards;
    public static volatile SingularAttribute<Movie, String> name;
    public static volatile SetAttribute<Movie, MovieActor> movieActors;
    public static volatile SetAttribute<Movie, MovieDirector> movieDirectors;
}
