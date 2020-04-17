package com.daos.controll;

import com.daos.DAO;
import com.entities.*;
import com.exceptions.DataBaseException;
import com.exceptions.InvalidTableNameException;
import com.util.DBData;
import com.util.JDBCUtil;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractController<T extends ObjectEntity>  implements DAO<T> {

    public AbstractController(Class<T> objectsClass) {
        this.objectsClass = objectsClass;
    }

    Class<T> objectsClass;

    protected JDBCUtil database;

    protected String getTableName(){
        if(objectsClass == Album.class) return "albums";
        if(objectsClass == Artist.class) return "artists";
        if(objectsClass == Chart.class) return "charts";
        if(objectsClass == Genre.class) return "genres";
        if(objectsClass == ClasamentEntry.class) return "clasaments";
        throw new IllegalArgumentException("Class not found: " + objectsClass.getName());
    }

    public final String getClassName(){
        return objectsClass.getName();
    }

    @Override
    public long count(){
        String sql = "select count(*) from " + getTableName();

        try {
            var data = database.select(sql);

            for (var key : data.getKeySet()) {
                return Long.parseLong(data.getData(key, 0));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (DataBaseException e) {
            e.printReason();
        }
        return 0;
    }

    @Override
    public Integer getNextId(){

        String sql = "select max(id)+1 from " + getTableName();

        try {
            var data = database.select(sql);

            for(var key: data.getKeySet()){
                return Integer.valueOf((data.getData(key,0)));
            }
        } catch (DataBaseException e) {
            e.printReason();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return 0;
    }

    public void deleteAllById(Iterable<Integer> ids) {
        try {
            database.delete(getTableName(),ids);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InvalidTableNameException e) {
            e.printReason();
        }
    }

    @Override
    public void deleteById(Integer id) {
        List<Integer> ids = new ArrayList<>();
        ids.add(id);
        deleteAllById(ids);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        List<Integer> ids = new ArrayList<>();
        entities.forEach((Consumer<T>) t -> ids.add(t.ID()));

        deleteAllById(ids);
    }

    protected abstract T createEntity(Map<String,String> values);

    @Override
    public List<T> findAll() {
        List<T> results = new ArrayList<>();

        String sql = "select * from " + getTableName();

        try {
            var data = database.select(sql);
            if (data != null) {
                for(int i = 0; i < data.getRows(); i++){
                    Map<String, String> values = new HashMap<>();

                    for(var key: data.getKeySet()){
                        values.put(key,data.getData(key,i));
                    }

                    results.add(createEntity(values));
                }
            }
        } catch (DataBaseException e) {
            e.printReason();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    @Override
    public Optional<T> findById(Integer id) {
        String sql = "select * from " + getTableName() + " where id = " + id;

        DBData data;

        try {
            data = database.select(sql);
            if (data != null) {
                for(int i = 0; i < data.getRows(); i++){
                    Map<String, String> values = new HashMap<>();

                    for(var key: data.getKeySet()){
                        values.put(key,data.getData(key,i));
                    }

                    return Optional.of(createEntity(values));
                }
            }
        } catch (DataBaseException e) {
            e.printReason();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return Optional.empty();
    }

    @Override
    public <S extends T> S save(S entity) {
        Map<String,String> values = entity.toMap();

        try {
            database.insert(getTableName(),values);
        } catch (InvalidTableNameException e) {
            e.printReason();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
}
