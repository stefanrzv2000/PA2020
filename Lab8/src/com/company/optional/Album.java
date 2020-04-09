package com.company.optional;

import com.company.compulsory.ArtistController;

public class Album {
    int id;
    String name;
    int artistId;
    int releaseYear;

    Artist artist = null;

    public Album(int id, String name, int artistId, int releaseYear) {
        this.id = id;
        this.name = name;
        this.artistId = artistId;
        this.releaseYear = releaseYear;

        ArtistController artistController = ArtistController.getInstance();
        if(artistController.getArtists().containsKey(artistId)){
            artist = artistController.getArtists().get(artistId);
        }
    }

    public String getName() {
        return name;
    }

    public int getArtistId() {
        return artistId;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public Artist getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        if(artist != null){
            return name + " - " + artist.getName() + " (" + releaseYear + ")";
        }
        return name + " - " + artistId + " (" + releaseYear + ")";
    }
}
