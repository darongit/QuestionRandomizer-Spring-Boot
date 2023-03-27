package com.example.questionrandomizer.question;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Question {
    @Id
    @SequenceGenerator(
            name = "question_generator",
            sequenceName = "question_generator",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_generator"
    )
    private Long id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String category;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    private boolean alreadySeen = false;
    public Question(String category, String content) {
        this.category = category;
        this.content = content;
    }
}
