package com.daos.repu;

import com.daos.Genres;
import com.entities.Genre;
import com.util.HibernateUtil;

import javax.persistence.Query;
import java.util.List;

public class GenreRepusitory extends AbstractRepusitory<Genre> implements Genres {

    private static Query FIND_BY_NAME(){
        return HibernateUtil.getSession().createNamedQuery("FIND_GENRE",Genre.class);
    }

    private static Query findByName(){
        return HibernateUtil.getSession().createQuery(
                "select g from Genre g where g.name like :name",
                Genre.class
        );
    }

    public GenreRepusitory(){
        objectsClass = Genre.class;
    }

    @Override
    public List<Genre> findByName(String name) {
        return findByName().setParameter("name", name).getResultList();
    }
}
