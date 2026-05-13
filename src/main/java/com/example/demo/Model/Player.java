package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Entity
public class Player {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String avatar;
    private Integer score;
    private Double lastAnswerTime;
    @ElementCollection
    private List<Integer> answeredQuestionIds = new ArrayList<>();
}