package com.daos.repu;

import com.daos.Artists;
import com.entities.Album;
import com.entities.Artist;
import com.util.HibernateUtil;
import com.util.PersistenceUtil;

import javax.persistence.Query;
import java.util.List;

public class ArtistRepusitory extends AbstractRepusitory<Artist> implements Artists {

    public ArtistRepusitory(){
        objectsClass = Artist.class;
    }

    private static final Query FIND_BY_NAME = PersistenceUtil.getEntityManager()
            .createQuery(
                    "select g from Artist g where g.name like :name",
                    Artist.class
            );

    private static final Query FIND_BY_COUNTRY = PersistenceUtil.getEntityManager()
            .createQuery(
                    "select g from Artist g where g.country like :country",
                    Artist.class
            );

    private static final Query FIND_BY_NAME_2 = PersistenceUtil.getEntityManager()
            .createNamedQuery("Artists.FIND_BY_NAME",Artist.class);


    @Override
    public List<Artist> findByName(String name) {
        return FIND_BY_NAME_2.setParameter("name",name).getResultList();
    }

    @Override
    public double getArtistScore(int id) {
        final double[] score = {0};

        PersistenceUtil.getEntityManager().createQuery(
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
