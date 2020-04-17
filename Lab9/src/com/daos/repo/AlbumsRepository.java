package com.daos.repo;

import com.daos.Albums;
import com.entities.Album;
import com.util.HibernateUtil;
import com.util.PersistenceUtil;

import javax.persistence.Query;
import java.util.List;

public class AlbumsRepository extends AbstractRepository<Album> implements Albums {

    private static final Query FIND_BY_NAME = HibernateUtil.getSession()
            .createQuery(
                    "select g from Album g where g.name like :name",
                    Album.class
            );

    private static final Query FIND_BY_ARTIST = HibernateUtil.getSession()
            .createQuery(
                    "select a from Album a where artistId = :artistId",
                    Album.class
            );

    private static final Query FIND_BY_GENRE = HibernateUtil.getSession()
            .createQuery(
                    "select g from Album g where g.genreId = :genreId",
                    Album.class
            );

    public AlbumsRepository(){
        this.objectsClass = Album.class;
    }

    @Override
    public List<Album> findByName(String name) {
        return FIND_BY_NAME.setParameter("name",name).getResultList();
    }

    @Override
    public List<Album> findByArtist(int id) {
        return FIND_BY_ARTIST.setParameter("artistId",id).getResultList();
    }

    @Override
    public void assignRandomGenres() {

    }

    @Override
    public List<Album> findByGenre(String genre) {
        return null;
    }

    @Override
    public List<Album> findByGenreID(Integer id) {
        return FIND_BY_GENRE.setParameter("genreId",id).getResultList();
    }

}
