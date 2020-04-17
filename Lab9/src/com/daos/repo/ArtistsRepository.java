package com.daos.repo;

import com.daos.Artists;
import com.entities.Artist;
import com.util.HibernateUtil;
import com.util.PersistenceUtil;

import javax.persistence.Query;
import java.util.List;
import java.util.function.Consumer;

public class ArtistsRepository extends AbstractRepository<Artist> implements Artists {

    public static Query FIND_BY_COUNTRY = PersistenceUtil.getEntityManager().createQuery(
            "select a from Artist a where country = :country",
            Artist.class
    );
    public static Query FIND_BY_NAME = PersistenceUtil.getEntityManager().createQuery(
            "select a from Artist a where country = :country",
            Artist.class
    );

    public ArtistsRepository(){
        this.objectsClass = Artist.class;
    }

    @Override
    public List<Artist> findByName(String name) {
        return null;
    }

    @Override
    public double getArtistScore(int id) {

        final double[] score = {0};

        HibernateUtil.getSession().createQuery(
                "select c.place from ClasamentEntry c where c.artistId = :aid",
                Integer.class
        ).setParameter("aid",id).getResultList()
        .forEach(integer -> score[0] +=50.0/integer);

        return score[0];
    }

    @Override
    public List<Artist> findByCountry(String country) {
        return FIND_BY_COUNTRY.setParameter("country",country).getResultList();
    }

}
