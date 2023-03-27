package com.example.questionrandomizer.front;

import com.example.questionrandomizer.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class FrontService {
    private QuestionService questionService;
    @Autowired
    public FrontService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public String mainPage(Model model) {
        return "html/index";
    }
}
