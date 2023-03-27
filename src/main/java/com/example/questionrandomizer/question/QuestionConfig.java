package com.example.questionrandomizer.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuestionConfig implements CommandLineRunner {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void run(String... args) throws Exception {
        questionRepository.save(new Question("Lifestyle", "Whats your...."));
        questionRepository.save(new Question("Lifestyle", "What do you like...."));
        questionRepository.save(new Question("Relationships", "How long...."));
    }
}
