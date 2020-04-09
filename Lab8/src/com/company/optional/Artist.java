package com.company.optional;

import java.util.HashMap;
import java.util.Map;

public class Artist {
    private static Map<Integer,Artist> artists = new HashMap<>();

    private int id;
    private String name;
    private String country;

    public Artist(int id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
        artists.put(id,this);
    }

    public static Artist getById(int id){
        if(artists.containsKey(id)) return artists.get(id);
        return null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return name + " (" + country + ")";
    }
}
