package com.company.compulsory;

import com.company.exceptions.DataBaseException;
import com.company.exceptions.InvalidTableNameException;
import com.company.optional.Artist;
import com.company.optional.DBData;
import com.company.optional.ScoreBoard;
import com.company.optional.ScoreBoardEntry;
import com.github.javafaker.Faker;
import javafx.util.Pair;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtistController {
    private static ArtistController instance;

    private Map<Integer, Artist> artists;

    private Database database;

    private int totalArtists = -1;

    private ArtistController(){
        artists = new HashMap<>();
        database = Database.getInstance();
    }

    public static ArtistController getInstance() {
        if(instance == null) instance = new ArtistController();
        return instance;
    }

    public int create(String name, String country) throws InvalidTableNameException, SQLException {

        Map<String,String> values = new HashMap<>();
        values.put("name","'" + name + "'");
        values.put("country","'" + country + "'");

        return database.insert("artists",values);
    }

    public List<Artist> findByName(String name) throws SQLException, DataBaseException {
        List<Artist> results = new ArrayList<>();

        String sql = "select * from artists where name = '" + name + "'";
        DBData data = database.select(sql);

        for(int i = 0; i < data.getRows(); i++){
            int id = Integer.parseInt(data.getData("id",i));
            String _name = data.getData("name",i);
            String country = data.getData("country",i);
            Artist artist = new Artist(id,_name,country);
            artists.put(id,artist);
            results.add(artist);
        }

        return results;
    }

    public int createRandomArtist() throws InvalidTableNameException, SQLException {
        Faker faker = new Faker();
        String name =  faker.gameOfThrones().character() + " " + faker.superhero().suffix();// + " - " + faker.superhero().name() + " - " + faker.gameOfThrones().city();
        //System.out.println("\n" + name);
        String country = faker.address().country();
        //System.out.println(country);
        if(name.contains("'")) return -1;
        if(country.contains("'")) return -1;
        return create(name,country);
    }

    public int createRandomArtist(String country) throws InvalidTableNameException, SQLException {
        Faker faker = new Faker();
        String name =  faker.gameOfThrones().character() + " " + faker.superhero().suffix();// + " - " + faker.superhero().name() + " - " + faker.gameOfThrones().city();
        //System.out.println("\n" + name);
        //String country = faker.address().country();
        //System.out.println(country);
        if(name.contains("'")) return -1;
        if(country.contains("'")) return -1;
        return create(name,country);
    }

    public int totalArtists() throws SQLException, DataBaseException {

        if(totalArtists > 0) return totalArtists;

        String sql = "select count(*) from artists";

        DBData data = database.select(sql);

        for( String key: data.getKeySet()) {
            totalArtists = Integer.parseInt(data.getData(key,0));
            return totalArtists;
        }
        return 1;
    }

    public Map<Integer, Artist> getArtists() {
        return artists;
    }

    public Artist findById(int id) throws SQLException, DataBaseException {

        if(artists.containsKey(id)) return artists.get(id);

        String sql = "select * from artists where id = " + id;
        DBData data = database.select(sql);

        Artist artist = null;

        for(int i = 0; i < data.getRows(); i++){
            int _id = Integer.parseInt(data.getData("id",i));
            String _name = data.getData("name",i);
            String country = data.getData("country",i);
            artist = new Artist(id,_name,country);
            artists.put(_id,artist);
        }

        return artist;
    }

    public double getArtistScore(int id) throws SQLException, DataBaseException {

        double score = 0;

        if(id < 0 | id > totalArtists()) return score;

        String sql = "select * from clasaments where artist_id = " + id;

        var data = database.select(sql);

        //System.out.println("artist id: #no: " + data.getRows());

        for(int i = 0; i < data.getRows(); i++){
            int place = Integer.parseInt(data.getData("place",i));
            score += 50.0 / place;
        }

        return score;
    }

    public ScoreBoard scoreBoard(String country) throws SQLException, DataBaseException {

        String sql = "select id from artists where country = '" + country + "'";

        DBData data = database.select(sql);

        if(data.getRows() <= 0){
            country = "all around the globe";
            sql = "select id from artists";
            data = database.select(sql);
        }

        ScoreBoard scoreBoard = new ScoreBoard("Best artists from " + country);

        for(int i = 0; i < data.getRows(); i++){
            int artistId = Integer.parseInt(data.getData("id",i));

            double score = getArtistScore(artistId);

            scoreBoard.addEntry(new ScoreBoardEntry(artistId,score));
        }

        return scoreBoard;
    }
}
