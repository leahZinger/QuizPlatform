package com.example.demo.Controller;

import com.example.demo.Model.Question;
import com.example.demo.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/upload-questions")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file, @RequestParam("quizCode") String quizCode) {
        try {
            quizService.importQuestionsFromExcel(file, quizCode);
            return ResponseEntity.ok("Questions uploaded successfully for code: " + quizCode);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/questions/{quizCode}")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable String quizCode) {
        List<Question> questions = quizService.getQuestionsByCode(quizCode);
        return ResponseEntity.ok(questions);
    }
}