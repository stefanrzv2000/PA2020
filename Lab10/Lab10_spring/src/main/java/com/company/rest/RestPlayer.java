package com.company.rest;


import com.company.game.Player;

public class RestPlayer {
    int id;
    String username;

    public RestPlayer(){}

    public RestPlayer(int id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public String toString() {
        return "RestPlayer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
