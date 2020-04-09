package com.company.optional;

import com.company.compulsory.ArtistController;
import com.company.exceptions.DataBaseException;

import java.sql.SQLException;

public class ScoreBoardEntry implements Comparable<ScoreBoardEntry> {
    private Artist artist;
    private double score;

    public ScoreBoardEntry(int artistId, double score) throws SQLException, DataBaseException {
        artist = ArtistController.getInstance().findById(artistId);
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
