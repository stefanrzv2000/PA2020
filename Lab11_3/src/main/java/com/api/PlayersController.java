package com.api;

import com.entities.Player;
import com.exception.PlayerNameTakenException;
import com.exception.PlayerNotFoundException;
import com.repo.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayersController {

    private static PlayersController instance;
    public static PlayersController getInstance(){
        return instance;
    }

    public PlayersController(){
        instance = this;
    }

    @Autowired
    private PlayersRepository players;

    @GetMapping("/all")
    public List<Player> getAll(){
        return (List<Player>) players.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getById(@PathVariable int id){
        System.out.println("GET request: /players/" + id);
        var o = players.findById(id);
        if(o.isEmpty()){
            throw new PlayerNotFoundException(id);
        }
        var re = new ResponseEntity<>(o.get(),HttpStatus.OK);
        System.out.println("response: " + re);
        return re;
    }

    public boolean containsId(int id){
        var o = players.findById(id);
        return o.isPresent();
    }

    @GetMapping("/name/{name}")
    public Player getByName(@PathVariable String name){
        Player player = findByName(name);
        if(player == null){
            throw new PlayerNotFoundException(name);
        }
        return player;
    }

    @GetMapping("/exists/{name}")
    public ResponseEntity<Integer> existsByName(@PathVariable String name){
        Player player = findByName(name);
        int id = player==null?-1:player.getId();
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

    public Player findByName(String name){
        var o = players.findByUsername(name);
        if(o.isEmpty()){
            return null;
        }
        return o.get(0);
    }

    @PostMapping("/add/{name}")
    public ResponseEntity<Player> addPlayerByName(@PathVariable String name){
        Player p = addByName(name);
        if(p == null){
            throw new PlayerNameTakenException(name);
        }
        return new ResponseEntity<>(p,HttpStatus.OK);
    }


    public Player addByName(String name){
        if(players.findByUsername(name).isEmpty()){

            Player player = new Player();
            player.setUsername(name);
            return players.save(player);
        }
        return null;
    }

    @PutMapping("/{id}/{name}")
    public ResponseEntity<String> putName(@PathVariable int id, @PathVariable String name){
        if(!containsId(id)){
            throw new PlayerNotFoundException(id);
        }
        if(findByName(name) != null){
            throw new PlayerNameTakenException(name);
        }
        var o =  players.findById(id);
        assert (o.isPresent());
        var player = o.get();
        player.setUsername(name);
        players.save(player);
        return new ResponseEntity<>("Player name updated sucessfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id){
        if(!containsId(id)){
            throw new PlayerNotFoundException(id);
        }
        players.deleteById(id);
        return new ResponseEntity<>("Player deleted succesfully",HttpStatus.OK);

    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<String> deleteByName(@PathVariable String name){
        Player player = getByName(name);
        if(player == null){
            throw new PlayerNotFoundException(name);
        }

        players.delete(player);
        return new ResponseEntity<>("Player " + name + " deleted succesfully",HttpStatus.OK);

    }
}
