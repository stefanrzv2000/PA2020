package com.daos.controll;

import com.daos.Artists;
import com.entities.Artist;
import com.exceptions.DataBaseException;
import com.util.DBData;

import java.sql.SQLException;
import java.util.*;

public class ArtistController extends AbstractController<Artist> implements Artists {

    public ArtistController(){
        super(Artist.class);
    }

    @Override
    protected Artist createEntity(Map<String, String> values) {
        return Artist.fromMap(values);
    }

    @Override
    public List<Artist> findByName(String name) {
        List<Artist> results = new ArrayList<>();

        String sql = "select * from " + getTableName() + " where name = '" + name + "'";

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
    public double getArtistScore(int id) {
        double score = 0;

        if(id < 0 | id > count()) return score;

        String sql = "select * from clasaments where artist_id = " + id;

        try {
            var data = database.select(sql);

            for (int i = 0; i < data.getRows(); i++) {
                int place = Integer.parseInt(data.getData("place", i));
                score += 50.0 / place;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (DataBaseException e) {
            e.printReason();
        }
        return score;
    }

    @Override
    public List<Artist> findByCountry(String country) {
        return null;
    }
}
