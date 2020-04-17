package com.daos.repo;

import com.daos.Genres;
import com.entities.Genre;
import com.util.HibernateUtil;

import javax.persistence.*;
import java.util.List;

public class GenreRepository extends AbstractRepository<Genre> implements Genres {

    private static final Query FIND_BY_NAME = HibernateUtil.getSession().createQuery(
            "select g from Genre g where g.name like :name",Genre.class
    );

    public GenreRepository(){
        objectsClass = Genre.class;
    }


    @Override
    public List<Genre> findByName(String name) {

        return FIND_BY_NAME.setParameter("name", name ).getResultList();

    }

}
