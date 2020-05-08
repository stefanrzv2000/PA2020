package com.company.rest;

import net.minidev.json.JSONObject;

import java.sql.Date;
import java.util.Objects;

public class RestGame {
    private int id;
    private int id1;
    private int id2;
    private int size;
    private String gameId;
    private Integer win;
    private String dataJoc;
    private String sgfResume;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public String getDataJoc() {
        return dataJoc;
    }

    public void setDataJoc(String dataJoc) {
        this.dataJoc = dataJoc;
    }

    public String getSgfResume() {
        return sgfResume;
    }

    public void setSgfResume(String sgfResume) {
        this.sgfResume = sgfResume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestGame game = (RestGame) o;
        return id == game.id &&
                id1 == game.id1 &&
                id2 == game.id2 &&
                Objects.equals(win, game.win) &&
                Objects.equals(dataJoc, game.dataJoc) &&
                Objects.equals(sgfResume, game.sgfResume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, id1, id2, win, dataJoc, sgfResume);
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String name) {
        this.gameId = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public JSONObject toJSON(){
        var json = new JSONObject();

        json.put("id",id);
        json.put("id1",id1);
        json.put("id2",id2);
        json.put("size",size);
        json.put("gameId",gameId);
        json.put("win",win);
        json.put("dataJoc",dataJoc);
        json.put("sgfResume",sgfResume);

        return json;
    }
}
