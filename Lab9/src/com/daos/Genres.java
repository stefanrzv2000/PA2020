package com.daos;

import com.entities.Genre;

import java.util.List;

public interface Genres extends DAO<Genre> {
    List<Genre> findByName(String name);

    default void deleteByName(String name){
        var existent = findByName(name);

        if(existent.isEmpty()){
            System.out.println("The genre " + name + " does not exist!");
        }else {
            deleteAll(findByName(name));
        }
    }

    default Genre addGenre(String name){
        var existent = findByName(name);

        for(var g: existent){
            System.out.println("The genre " + name + " already exists!");
            return g;
        }

        Genre genre = new Genre();
        genre.setName(name);

        return save(genre);
    }
}
