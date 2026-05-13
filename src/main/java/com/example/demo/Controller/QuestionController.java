package com.example.demo.Controller;

import com.example.demo.Model.Question;
import com.example.demo.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/quiz/{quizCode}")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable String quizCode) {
        return ResponseEntity.ok(quizService.getQuestionsByCode(quizCode));
    }
}