package com.daos.controll;

import com.daos.Genres;
import com.entities.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GenreController extends AbstractController<Genre> implements Genres {

    public GenreController(){
        super(Genre.class);
    }

    @Override
    public List<Genre> findByName(String name) {
        return null;
    }

    @Override
    public Integer getNextId() {
        return null;
    }

    @Override
    protected Genre createEntity(Map<String, String> values) {
        return Genre.fromMap(values);
    }

    @Override
    public void delete(Genre entity) {

    }

    @Override
    public List<Genre> findAll() {
        return null;
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        return Optional.empty();
    }

}
