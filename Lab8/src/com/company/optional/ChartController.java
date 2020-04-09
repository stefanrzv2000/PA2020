package com.company.optional;

import com.company.compulsory.Database;
import com.company.exceptions.DataBaseException;
import com.github.javafaker.Faker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ChartController {
    private static ChartController instance;

    private Database database;

    private ChartController(){
        database = Database.getInstance();
    }

    public static ChartController getInstance() {
        if(instance == null) instance = new ChartController();
        return instance;
    }

    public int createChartByCountry(String country, int count) throws SQLException, DataBaseException {

        if(country.contains("'")) return -1;

        Faker faker = new Faker();
        String name = country + " Top " + count + " " + faker.color().name();

        String sql2 = "select id from charts where name = '" + name + "'";

        var data1 = database.select(sql2);
        if(data1.getRows() > 0) return -1;

        System.out.println(name);

        String sql1 = "select albums.id, albums.artist_id from albums join artists on albums.artist_id = artists.id where country = '" + country + "' order by rand() limit " + count;

        System.out.println(sql1);

        var data = database.select(sql1);

        Map<String,String> values = new HashMap<>();
        values.put("name","'" + name + "'");
        values.put("places", String.valueOf(data.getRows()));

        int chartId = database.insert("charts",values);

        for (int i=0; i<data.getRows(); i++){
            Map<String,String> clasament = new HashMap<>();
            clasament.put("chart_id", String.valueOf(chartId));
            clasament.put("album_id", data.getData("id",i));
            clasament.put("artist_id", data.getData("artist_id",i));
            clasament.put("place", String.valueOf(i+1));

            database.insert("clasaments",clasament);
        }

        return chartId;
    }

    public int createChartByPeriod(int from, int to, int count) throws SQLException, DataBaseException {
        Faker faker = new Faker();
        String name = "Top " + count + " " + faker.color().name() + " " + from + "-" + to;

        String sql2 = "select id from charts where name = '" + name + "'";

        var data1 = database.select(sql2);
        if(data1.getRows() > 0) return -1;

        System.out.println(name);

        String sql1 = "select albums.id, albums.artist_id from albums join artists on albums.artist_id = artists.id where release_year between " + from + " and " + to + " order by rand() limit " + count;

        System.out.println(sql1);

        var data = database.select(sql1);

        Map<String,String> values = new HashMap<>();
        values.put("name","'" + name + "'");
        values.put("places", String.valueOf(data.getRows()));


        int chartId = database.insert("charts",values);

        for (int i=0; i<data.getRows(); i++){
            Map<String,String> clasament = new HashMap<>();
            clasament.put("chart_id", String.valueOf(chartId));
            clasament.put("album_id", data.getData("id",i));
            clasament.put("artist_id", data.getData("artist_id",i));
            clasament.put("place", String.valueOf(i+1));

            database.insert("clasaments",clasament);
        }

        return chartId;

    }
}
