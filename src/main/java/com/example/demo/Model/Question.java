package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quizCode;
    private String content;
    private String correctAnswer;
    private String option2;
    private String option3;
    private String option4;
    private int points;
    private int answerTime;

    public int getPoints() {
        return points;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getAnswerTime() {
        return answerTime;
    }

    public String getAns1() { return correctAnswer; }
    public String getAns2() { return option2; }
    public String getAns3() { return option3; }
    public String getAns4() { return option4; }

    public void setId(Long id) { this.id = id; }
    public void setQuizCode(String quizCode) { this.quizCode = quizCode; }
    public void setContent(String content) { this.content = content; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
    public void setOption2(String option2) { this.option2 = option2; }
    public void setOption3(String option3) { this.option3 = option3; }
    public void setOption4(String option4) { this.option4 = option4; }
    public void setPoints(int points) { this.points = points; }
    public void setAnswerTime(int answerTime) { this.answerTime = answerTime; }

    // --- Getters בסיסיים ---
    public Long getId() { return id; }
    public String getQuizCode() { return quizCode; }
    public String getContent() { return content; }
}