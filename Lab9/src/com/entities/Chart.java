package com.entities;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "charts", schema = "MusicAlbums", catalog = "")
public class Chart extends ObjectEntity<Chart>{
    private int id;
    private String name;
    private int places;
    private Integer active;

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
    @Column(name = "places", nullable = false)
    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    @Basic
    @Column(name = "active", nullable = true)
    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chart chart = (Chart) o;
        return id == chart.id &&
                places == chart.places &&
                Objects.equals(name, chart.name) &&
                Objects.equals(active, chart.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, places, active);
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
        values.put("places", String.valueOf(places));
        values.put("active", String.valueOf(active));

        return values;
    }

    public static Chart fromMap(Map<String, String> values) {
        return null;
    }
}
