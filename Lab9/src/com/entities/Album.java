package com.entities;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "albums", schema = "MusicAlbums")
@org.hibernate.annotations.NamedQuery(
        name = "Albums.findByArtist",
        query = "select a from Album a where a.artistId = :artistId"
)
public class Album extends ObjectEntity<Album> {
    private int id;
    private String name;
    private int artistId;
    private Integer releaseYear;
    private Integer genreId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "artist_id", nullable = false)
    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    @Basic
    @Column(name = "release_year", nullable = true)
    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Basic
    @Column(name = "artist_id")
    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return id == album.id &&
                artistId == album.artistId &&
                Objects.equals(name, album.name) &&
                Objects.equals(releaseYear, album.releaseYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, artistId, releaseYear);
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artistId=" + artistId +
                ", releaseYear=" + releaseYear +
                ", genre = " + genreId +
                '}';
    }

    @Override
    public int ID() {
        return getId();
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String > values = new HashMap<>();

        values.put("id", String.valueOf(id));
        values.put("name",name);
        values.put("artistId", String.valueOf(artistId));
        values.put("genreId", String.valueOf(genreId));
        values.put("releaseYear", String.valueOf(releaseYear));

        return values;
    }


    public static Album fromMap(Map<String, String> values) {
        Album album = new Album();

        album.setArtistId(Integer.parseInt(values.get("artistId")));
        album.setId(Integer.parseInt(values.get("artistId")));
        album.setGenreId(Integer.parseInt(values.get("artistId")));
        album.setReleaseYear(Integer.parseInt(values.get("releaseYear")));
        album.setName(values.get("name"));

        return album;

    }
}
