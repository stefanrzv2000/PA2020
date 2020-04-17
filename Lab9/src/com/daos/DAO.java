package com.daos;

import com.entities.ObjectEntity;
import com.exceptions.DataBaseException;
import com.exceptions.InvalidTableNameException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface DAO<T extends ObjectEntity> {

    long count();

    Integer getNextId();

    default void delete(T entity) {
        deleteById(entity.ID());
    }

    default void deleteAll(){
        deleteAll(findAll());
    }

    default void deleteAll(Iterable<? extends T> entities) {
        for(var entity: entities){
            deleteById(entity.ID());
        }
    }

    void deleteById(Integer id);

    default boolean existsById(Integer id) {
        return findById(id).isPresent();
    }
    //Returns whether an entity with the given id exists.

    List<T> findAll();
    //Returns all instances of the type.

    default List<T> findAllById(Iterable<Integer> ids) {
        List<T> results = new ArrayList<>();

        for(var id: ids){
            findById(id).ifPresent(results::add);
        }
        return results;
    }
    //Returns all instances of the type T with the given IDs.

    Optional<T> findById(Integer id);
    //Retrieves an entity by its id.


    <S extends T> S save(S entity);
    //Saves a given entity.

    default <S extends T> List<S> saveAll(Iterable<S> entities){
        List<S> added = new ArrayList<>();

        for(var entity: entities){
            save(entity);
            added.add(entity);
        }
        return added;
    }
    //Saves all given entities.
}
