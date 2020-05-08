package com.repo;

import com.entities.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GamesRepository extends CrudRepository<Game, Integer> {

    public List<Game> findByGameId(String gameId);

    public List<Game> findById1OrId2(int id1, int id2);

}
