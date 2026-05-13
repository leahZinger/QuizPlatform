package com.example.demo.Controller;

import com.example.demo.Model.Quiz;
import com.example.demo.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "*")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        Quiz newQuiz = quizService.addQuiz(quiz);
        return ResponseEntity.ok(newQuiz);
    }

    @GetMapping("/check/{code}")
    public ResponseEntity<Boolean> checkQuizCode(@PathVariable String code) {
        return ResponseEntity.ok(true);
    }
}