package com.example.demo.Controller;

import com.example.demo.Model.Player;
import com.example.demo.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    // הצטרפות שחקן למשחק
    @PostMapping("/join")
    public ResponseEntity<Player> joinGame(@RequestBody Player player) {
        return ResponseEntity.ok(playerService.joinGame(player));
    }

    @PostMapping("/{playerId}/submit")
    public Player submitAnswer(
            @PathVariable Integer playerId,
            @RequestParam Integer questionId,
            @RequestParam Integer answerIndex,
            @RequestParam Double timeTaken) {
        return playerService.submitAnswer(playerId, questionId, answerIndex, timeTaken);
    }
    @GetMapping("/winner")
    public Player getWinner() {
        return playerService.getWinner();
    }
}