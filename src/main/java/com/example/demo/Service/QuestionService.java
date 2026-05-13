package com.example.demo.Service;

import com.example.demo.Model.Player;
import com.example.demo.Model.Question;
import com.example.demo.Model.Quiz;
import com.example.demo.Repository.QuestionRepository;
import com.example.demo.Repository.PlayerRepository; // הוספתי
import com.example.demo.Repository.QuizRepository;     // הוספתי
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private QuizRepository quizRepository;

    public List<Question> getQuestionsByCode(String quizCode) {
        List<Question> questions = questionRepository.findByQuizCode(quizCode);

        Collections.shuffle(questions);

        return questions;
    }
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Quiz finishQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        List<Player> allPlayers = playerRepository.findAll();
        Player winner = allPlayers.stream()
                .max(Comparator.comparingInt(Player::getScore))
                .orElse(null);

        if (winner != null) {
            quiz.setWinnerName(winner.getName());
            quiz.setWinnerScore(winner.getScore());
            quiz.setEndTime(LocalDateTime.now());
        }

        return quizRepository.save(quiz);
    }

    public List<String> getShuffledAnswers(Question question) {
        List<String> answers = new ArrayList<>();
        answers.add(question.getAns1());
        answers.add(question.getAns2());
        answers.add(question.getAns3());
        answers.add(question.getAns4());

        Collections.shuffle(answers);
        return answers;
    }
}