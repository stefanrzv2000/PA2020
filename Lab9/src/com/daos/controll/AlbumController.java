package com.daos.controll;

import com.daos.Albums;
import com.entities.Album;

import java.util.List;
import java.util.Map;

public class AlbumController extends AbstractController<Album> implements Albums {
    public AlbumController() {
        super(Album.class);
    }

    @Override
    public List<Album> findByName(String name) {
        return null;
    }

    @Override
    public List<Album> findByArtist(int id) {
        return null;
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
        return null;
    }

    @Override
    protected Album createEntity(Map<String, String> values) {
        return Album.fromMap(values);
    }
}
