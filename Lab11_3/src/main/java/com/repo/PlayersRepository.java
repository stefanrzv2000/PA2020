package com.repo;

import com.entities.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayersRepository extends CrudRepository<Player,Integer> {
    public List<Player> findByUsername(String username);
}
