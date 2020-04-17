package com.graph;

import com.entities.Album;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {

    Album album;
    Set<Node> artistNeighbours = new HashSet<>();
    Set<Node> genreNeighbours = new HashSet<>();

    Set<Node> allNeighbours = new HashSet<>();

    public Node(Album album) {
        this.album = album;
    }

    public void addArtistNeighbour(Node node){
        artistNeighbours.add(node);
        allNeighbours.add(node);
    }

    public void addGenreNeighbour(Node node){
        genreNeighbours.add(node);
        allNeighbours.add(node);
    }

    public int getNeighbourCount(){
        return allNeighbours.size();
    }

}
