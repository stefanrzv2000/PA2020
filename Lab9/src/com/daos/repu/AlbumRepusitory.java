package com.daos.repu;

import com.daos.Albums;
import com.daos.DAOAbstractFactory;
import com.daos.FactoryTypes;
import com.daos.Genres;
import com.entities.Album;
import com.entities.Genre;
import com.util.PersistenceUtil;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepusitory extends AbstractRepusitory<Album> implements Albums {

    private static final Query FIND_BY_NAME = PersistenceUtil.getEntityManager()
            .createQuery(
                    "select g from Album g where g.name like :name",
                    Album.class
            );

    private static final Query FIND_BY_ARTIST = PersistenceUtil.getEntityManager()
            .createQuery(
                    "select g from Album g where g.artistId = :artistId",
                    Album.class
            );

    private static final Query FIND_BY_GENRE = PersistenceUtil.getEntityManager()
            .createQuery(
                    "select g from Album g where g.genreId = :genreId",
                    Album.class
            );

    @Override
    public List<Album> findByName(String name) {
        return FIND_BY_NAME.setParameter("name",name).getResultList();
    }

    @Override
    public List<Album> findByArtist(int id) {
        return FIND_BY_ARTIST.setParameter("artistId",id).getResultList();
    }

    @Override
    public void assignRandomGenres(){
        var em = PersistenceUtil.getEntityManager();

        Genres genres = (Genres) DAOAbstractFactory.getFactory().getDAO(Genre.class, FactoryTypes.PERSISTENCE);

        List<Genre> genreList = genres.findAll();

        int genreCount = genreList.size();

        em.getTransaction().begin();

        em.createQuery(
                "select a from Album a",
                Album.class
        ).getResultList()
        .forEach(album -> {
            album.setGenreId(genreList.get((int) (Math.random()*genreCount)).getId());
            em.persist(album);
        });

        em.getTransaction().commit();

    }

    @Override
    public List<Album> findByGenre(String genre) {

        List<Album> artists = new ArrayList<>();

        var genres = new GenreRepusitory().findByName(genre);

        if (!genres.isEmpty()){
            artists = findByGenreID(genres.get(0).getId());
        }

        return artists;
    }

    @Override
    public List<Album> findByGenreID(Integer id) {
        return FIND_BY_GENRE.setParameter("genreId",id).getResultList();
    }
}
