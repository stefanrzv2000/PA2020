package com.bot;

import com.exception.ExistentPieceException;
import com.exception.GameNotFoundException;
import com.game.Game;
import com.game.Piece;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gomoku")
public class BotController {

    @PostMapping("/new/{size}")
    public ResponseEntity<Integer> createGame(@PathVariable int size){
        Game game = new Game(size);

        return new ResponseEntity<>(game.getId(), HttpStatus.OK);
    }

    @PostMapping("/{gid}/move/{x}/{y}")
    public ResponseEntity<Piece> nextMove(@PathVariable int gid, @PathVariable int x, @PathVariable int y){
        Game game = Game.get(gid);
        if(game == null){
            throw new GameNotFoundException(gid);
        }

        Piece piece = new Piece(x,y,false);

        if(game.addPiece(piece)){
            Piece move = game.move();
            return new ResponseEntity<>(move,HttpStatus.OK);
        }
        else{
            throw new ExistentPieceException(piece);
        }

    }
}
