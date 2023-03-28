package com.example.questionrandomizer.front;

import com.example.questionrandomizer.question.Question;
import com.example.questionrandomizer.question.QuestionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class FrontService {
    private QuestionService questionService;
    @Autowired
    public FrontService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public String mainPage(Model model) {
        List<Category> categories = new ArrayList<>();
        categories.add(Category.getAll());
        questionService.getAllCategories().forEach(category -> categories.add(new Category(category)));
        model.addAttribute("categories", categories);
        return "html/index";
    }
    public String settingsPage(Model model) {
        model.addAttribute("categories", questionService.getAllCategories());
        return "html/settings";
    }

    public String randomQuestionPageByCategory(Model model, String category) {
        Question question = questionService.getRandomQuestionByCategory(category);
        if (question == null) {
            question = new Question("Error", "Wrong category or no available questions.");
        }
        model.addAttribute("question", question);
        model.addAttribute("category", new Category(question.getCategory()));
        return "html/question";
    }
}
