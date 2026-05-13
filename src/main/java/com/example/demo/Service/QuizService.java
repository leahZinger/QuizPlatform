package com.example.demo.Service;

import com.example.demo.Model.Question;
import com.example.demo.Model.Quiz;
import com.example.demo.Repository.QuestionRepository;
import com.example.demo.Repository.QuizRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Quiz addQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public void importQuestionsFromExcel(MultipartFile file, String quizCode) throws Exception {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;
            Question q = new Question();
            q.setQuizCode(quizCode);
            q.setContent(row.getCell(0).getStringCellValue());
            q.setCorrectAnswer(row.getCell(1).getStringCellValue());
            q.setOption2(row.getCell(2).getStringCellValue());
            q.setOption3(row.getCell(3).getStringCellValue());
            q.setOption4(row.getCell(4).getStringCellValue());

            questionRepository.save(q);
        }
        workbook.close();
    }

    public List<Question> getQuestionsByCode(String quizCode) {
        List<Question> questions = questionRepository.findByQuizCode(quizCode);
        Collections.shuffle(questions); // ערבוב השאלות
        return questions;
    }
}