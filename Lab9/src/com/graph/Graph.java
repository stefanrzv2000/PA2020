package com.graph;

import com.daos.*;
import com.entities.Album;
import com.entities.Artist;
import com.entities.Genre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Graph {

    Map<Integer,Node> nodes = new HashMap<>();

    public Graph() {
        this(1000000);
    }

    public Graph(long maxNodes){

        var factory = DAOAbstractFactory.getFactory();

        Albums albums = (Albums) factory.getDAO(Album.class, FactoryTypes.HIBERNATE);
        Artists artists = (Artists) factory.getDAO(Artist.class,FactoryTypes.HIBERNATE);
        Genres genres = (Genres) factory.getDAO(Genre.class,FactoryTypes.HIBERNATE);

        var albumList = albums.findAll();
        var artistList = artists.findAll();
        var genreList = genres.findAll();

        for(int i = 0 ; i < maxNodes & i < albumList.size(); i ++){
            nodes.put(albumList.get(i).getId(),new Node(albumList.get(i)));
        }

        for(var genre: genreList) {
            List<Integer> albumsOfGenre = new ArrayList<>();
            albums.findByGenreID(genre.getId()).stream()
                    .filter(album -> nodes.containsKey(album.getId()))
                    .mapToInt(Album::getId)
                    .forEach(albumsOfGenre::add);

            for (var album1 : albumsOfGenre) {
                for (var album2 : albumsOfGenre) {
                    if (!album1.equals(album2)) {
                        nodes.get(album1).addGenreNeighbour(nodes.get(album2));
                    }
                }
            }
        }

        for(var artist: artistList){
            List<Integer> albumsOfArtist = new ArrayList<>();
            albums.findByArtist(artist.getId()).stream()
                    .mapToInt(Album::getId)
                    .filter(nodes::containsKey)
                    .forEach(albumsOfArtist::add);

            for(var album1: albumsOfArtist){
                for(var album2: albumsOfArtist){
                    if(!album1.equals(album2)) {
                        nodes.get(album1).addArtistNeighbour(nodes.get(album2));
                    }
                }
            }
        }
    }

    public int getNodeCount(){
        return nodes.size();
    }

    public long getEdgeCount(){
        long count = 0;
        for(var nodeId: nodes.keySet()){
            count += nodes.get(nodeId).getNeighbourCount();
        }
        return count/2;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "\nnodes = " + getNodeCount() +
                "\nedges = " + getEdgeCount() +
                "\n}";
    }
}
