package com.daos;

import com.entities.Album;

import java.util.List;

public interface Albums extends DAO<Album> {

    List<Album> findByName(String name);

    List<Album> findByArtist(int id);

    void assignRandomGenres();

    List<Album> findByGenre(String genre);
    List<Album> findByGenreID(Integer id);

}
