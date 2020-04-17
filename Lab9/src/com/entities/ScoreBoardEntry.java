package com.entities;

import com.daos.controll.ArtistController;
import com.exceptions.DataBaseException;

import java.sql.SQLException;

public class ScoreBoardEntry implements Comparable<ScoreBoardEntry> {
    private Artist artist;
    private double score;

    public ScoreBoardEntry(int artistId, double score) {
        artist = new ArtistController().findById(artistId).get();
        this.score = score;
    }

    public ScoreBoardEntry(Artist artist, double score) {
        this.artist = artist;
        this.score = score;
    }

    @Override
    public int compareTo(ScoreBoardEntry other) {
        return -Double.compare(this.score,other.score);
    }

    public Artist getArtist() {
        return artist;
    }

    public double getScore() {
        return score;
    }
}
