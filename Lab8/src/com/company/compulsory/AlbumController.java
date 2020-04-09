package com.company.compulsory;

import com.company.exceptions.DataBaseException;
import com.company.exceptions.InvalidTableNameException;
import com.company.optional.Album;
import com.company.optional.Artist;
import com.company.optional.DBData;
import com.github.javafaker.Faker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumController {
    private static AlbumController instance;

    private Map<Integer, Album> albums;

    private Database database;

    private AlbumController(){
        albums = new HashMap<>();
        database = Database.getInstance();
    }

    public static AlbumController getInstance() {
        if(instance == null) instance = new AlbumController();
        return instance;
    }

    public int create(String name, int artistId, int releaseYear) throws InvalidTableNameException, SQLException {

        Map<String,String> values = new HashMap<>();
        values.put("name","'" + name + "'");
        values.put("artist_id", String.valueOf(artistId));
        values.put("release_year", String.valueOf(releaseYear));

        return database.insert("albums",values);
    }

    public int createRandomAlbum() throws DataBaseException, SQLException {
        Faker faker = new Faker();

        int startIndex = 1;

        String name = "";

        switch ((int) (Math.random() * 3)){
            case 0:
                name = faker.book().title();
                break;

            case 1:
                name = faker.beer().style() + " " + faker.superhero().descriptor();
                break;

            default:
                name = faker.commerce().material() + " " + faker.cat().breed();
                break;
        }

        int artistId = (int)(Math.random()*(ArtistController.getInstance().totalArtists()-startIndex) + startIndex);
        int releaseYear = (int)(Math.random()*120+1900);

        if(name.contains("'")) return -1;

        return create(name,artistId,releaseYear);
    }

    public List<Album> findByArtistId(int artistId) throws SQLException, DataBaseException {
        List<Album> results = new ArrayList<>();

        String sql = "select * from albums where artist_id = " + artistId;

        DBData data = database.select(sql);

        for(int i = 0; i < data.getRows(); i++){
            int id = Integer.parseInt(data.getData("id",i));
            int artist_id = Integer.parseInt(data.getData("artist_id",i));
            int release_year = Integer.parseInt(data.getData("release_year",i));
            String name = data.getData("name",i);

            Album album = new Album(id,name,artist_id,release_year);

            albums.put(id,album);

            results.add(album);
        }
        return results;
    }
}
