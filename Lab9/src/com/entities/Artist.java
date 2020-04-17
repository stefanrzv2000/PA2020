package com.entities;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "artists", schema = "MusicAlbums")
@NamedQuery(
        name = "Artists.FIND_BY_NAME",
        query = "select a from Artist a where a.name = :name"
)
public class Artist extends ObjectEntity<Artist> {
    private int id;
    private String name;
    private String country;

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
    @Column(name = "country", nullable = true, length = 100)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return id == artist.id &&
                Objects.equals(name, artist.name) &&
                Objects.equals(country, artist.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country);
    }

    @Override
    public String toString() {
        return "Artist " + id +
                ": " + name +
                " (" + country + ')';
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
        values.put("country", country);

        return values;
    }

    public static Artist fromMap(Map<String, String> values) {
        Artist artist = new Artist();

        artist.setId(Integer.parseInt(values.get("artistId")));
        artist.setName(values.get("name"));
        artist.setCountry(values.get("country"));

        return artist;

    }
}
