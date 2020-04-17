package com.entities;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "genres", schema = "MusicAlbums")
@NamedQuery(
        name = "FIND_GENRE",
        query = "select g from Genre g where g.name like :name"
)
public class Genre extends ObjectEntity<Genre> {
    private int id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre " + id + ". " + name;
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

        return values;
    }

    public static Genre fromMap(Map<String, String> values) {
        Genre genre = new Genre();

        genre.setId(Integer.parseInt(values.get("artistId")));
        genre.setName(values.get("name"));

        return genre;
    }
}
