package com.api;


import com.entities.Game;
import com.entities.Player;
import com.repo.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GamesController {

    private static GamesController instance;
    public static GamesController getInstance(){
        return instance;
    }

    public GamesController(){
        instance = this;
    }

    @Autowired
    GamesRepository games;

    @GetMapping("/all")
    public List<Game> getAll(){
        return (List<Game>) games.findAll();
    }

    @GetMapping("/{id}")
    public Game getByGameId(@PathVariable String id){
        var list = (List<Game>)games.findByGameId(id);
        if(list.isEmpty()){
            return null;
        }
        else {
            return list.get(0);
        }
    }

    @GetMapping("/exists_id/{id}")
    public ResponseEntity<Boolean> existsId(@PathVariable String id){
        var list = (List<Game>)games.findByGameId(id);
        return new ResponseEntity<>(!list.isEmpty(),HttpStatus.OK);
    }

    @GetMapping("/player/{name}")
    public List<Game> getByPlayerId(@PathVariable String name){
        Player player = PlayersController.getInstance().getByName(name);
        if(player == null){
            return new ArrayList<>();
        }
        int id = player.getId();
        return games.findById1OrId2(id,id);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> postGame(@RequestBody Game game){
        PlayersController players = PlayersController.getInstance();
        if(!players.containsId(game.getId1()) ||
            !players.containsId(game.getId2())){
            return new ResponseEntity<>("Player id's are not valid",HttpStatus.NOT_FOUND);
        }

        games.save(game);
        return new ResponseEntity<>("Game created successfully", HttpStatus.CREATED);
    }
}
