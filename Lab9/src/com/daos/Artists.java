package com.daos;

import com.entities.Artist;
import com.entities.ScoreBoard;
import com.entities.ScoreBoardEntry;

import java.util.List;

public interface Artists extends DAO<Artist> {
    List<Artist> findByName(String name);

    double getArtistScore(int id);

    List<Artist> findByCountry(String country);

    default ScoreBoard scoreBoard(String country){

        List<Artist> artists = findByCountry(country);

        if(artists.isEmpty()){
            country = "all around the globe";
            artists = findAll();
        }

        ScoreBoard scoreBoard = new ScoreBoard("Best artists from " + country);

        for(var artist: artists){
            double score = getArtistScore(artist.getId());

            scoreBoard.addEntry(new ScoreBoardEntry(artist, score));
        }

        return scoreBoard;
    }






}
