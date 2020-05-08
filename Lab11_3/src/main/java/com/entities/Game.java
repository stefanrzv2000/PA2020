package com.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Game {
    private int id;
    private int id1;
    private int id2;
    private int size;
    private String gameId;
    private Integer win;
    private String dataJoc;
    private String sgfResume;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    @Basic
    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    @Basic
    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    @Basic
    public String getDataJoc() {
        return dataJoc;
    }

    public void setDataJoc(String dataJoc) {
        this.dataJoc = dataJoc;
    }

    @Basic
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
        Game game = (Game) o;
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

    @Basic
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String name) {
        this.gameId = name;
    }

    @Basic
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
