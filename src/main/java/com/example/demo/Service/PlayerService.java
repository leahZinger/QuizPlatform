package com.example.demo.Service;

import com.example.demo.Controller.EventController; // ייבוא הקונטרולר
import com.example.demo.Model.Player;
import com.example.demo.Model.Question;
import com.example.demo.Repository.PlayerRepository;
import com.example.demo.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PlayerService {

    @Autowired private PlayerRepository playerRepository;
    @Autowired private QuestionRepository questionRepository;
    @Autowired private EventController eventController; // הזרקת המרכזייה

    public Player joinGame(Player player) {
        // בדיקה אם השם תפוס
        boolean exists = playerRepository.findAll().stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(player.getName()));

        if (exists) {
            throw new RuntimeException("השם הזה כבר תפוס, תבחר שם מקורי יותר!");
        }

        player.setScore(0);
        Player savedPlayer = playerRepository.save(player);
        eventController.broadcast("PLAYER_JOINED", savedPlayer.getName() + " נכנס לזירה!");
        return savedPlayer;
    }

    public Player submitAnswer(Integer playerId, Integer questionId, Integer answerIndex, Double timeTaken) {
        // 1. ולידציה: האם הישויות קיימות?
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("שחקן לא נמצא!"));
        Question question = questionRepository.findById(Long.valueOf(questionId))
                .orElseThrow(() -> new RuntimeException("שאלה לא נמצאה!"));

        // 2. ולידציה: מניעת רמאות - האם כבר ענה על השאלה הזו?
        if (player.getAnsweredQuestionIds().contains(questionId)) {
            throw new RuntimeException("כבר ענית על השאלה הזו, אין כפילויות!");
        }

        // 3. ולידציה: האם עמד בזמן? (נניח שניתן חריגה של שנייה אחת ללאג ברשת)
        if (timeTaken > question.getAnswerTime() + 1) {
            throw new RuntimeException("הזמן נגמר! התשובה לא התקבלה.");
        }

        boolean isCorrect = false;
        if (question.getCorrectAnswer().equals(answerIndex)) {
            isCorrect = true;
            double timeFactor = Math.max(0, 1 - (timeTaken / question.getAnswerTime()));
            int points = (int) (question.getPoints() * (0.5 + 0.5 * timeFactor));
            player.setScore(player.getScore() + points);
        }

        player.getAnsweredQuestionIds().add(questionId);
        player.setLastAnswerTime(timeTaken);
        Player updatedPlayer = playerRepository.save(player);

        eventController.broadcast("ANSWER_SUBMITTED", player.getName() + (isCorrect ? " צדק/ה!" : " טעה/תה..."));
        List<Player> topPlayers = playerRepository.findAll(Sort.by(Sort.Direction.DESC, "score"));
        eventController.broadcast("LEADERBOARD_UPDATE", topPlayers);
        List<Player> leaderboard = playerRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Player::getScore).reversed())
                .limit(10).toList();
        eventController.broadcast("LEADERBOARD_UPDATE", leaderboard);

        return updatedPlayer;
    }
    public Player getWinner() {
        List<Player> players = playerRepository.findAll();

        return players.stream()
                .sorted(Comparator.comparingInt(Player::getScore).reversed()
                        .thenComparingDouble(Player::getLastAnswerTime))
                .findFirst()
                .orElse(null);
    }
}