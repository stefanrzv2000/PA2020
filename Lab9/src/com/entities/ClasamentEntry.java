package com.entities;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "clasaments", schema = "MusicAlbums")
public class ClasamentEntry extends ObjectEntity<ClasamentEntry>{
    private int chartId;
    private int albumId;
    private int artistId;
    private int place;
    private int id;

    @Basic
    @Column(name = "chart_id", nullable = false)
    public int getChartId() {
        return chartId;
    }

    public void setChartId(int chartId) {
        this.chartId = chartId;
    }

    @Basic
    @Column(name = "album_id", nullable = false)
    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
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
    @Column(name = "place", nullable = false)
    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClasamentEntry that = (ClasamentEntry) o;
        return chartId == that.chartId &&
                albumId == that.albumId &&
                artistId == that.artistId &&
                place == that.place &&
                id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chartId, albumId, artistId, place, id);
    }

    @Override
    public int ID() {
        return getId();
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String > values = new HashMap<>();

        values.put("id", String.valueOf(id));
        values.put("artistId", String.valueOf(artistId));
        values.put("chartId", String.valueOf(chartId));
        values.put("albumId", String.valueOf(albumId));
        values.put("place", String.valueOf(place));

        return values;
    }

    public static ClasamentEntry fromMap(Map<String, String> values) {
        return null;
    }
}
