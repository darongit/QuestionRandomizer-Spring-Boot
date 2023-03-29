package com.example.questionrandomizer.front;

import com.example.questionrandomizer.question.Question;
import com.example.questionrandomizer.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
    public String resetPage(Model model, String category) {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("ALL CATEGORIES"));
        questionService.getAllCategories().forEach(category1 -> categories.add(new Category(category1)));
        if (category.strip().isBlank()) {
            System.out.println("Nothing");
            System.out.println(category);
        } else {
            questionService.resetQuestionsByCategory(category);
        }
        model.addAttribute("categories", categories);
        return "html/settings";
    }

    public String randomQuestionPageByCategory(Model model, String category) {
        String categoryFromUrl = category.strip().toUpperCase().replace("_", " ").replace("+", " ");
        Question question = null;
        if (!(questionService.getAllCategories().contains(categoryFromUrl)) && !categoryFromUrl.equals("ALL CATEGORIES")) {
            question = new Question("Error", "Wrong category or no available questions.");
            model.addAttribute("error", question);
            return "html/error";
        }
        else {
            question = questionService.getRandomQuestionByCategory(category);
        }
        if (question == null) {
            question = new Question("Error", "Wrong category or no available questions.");
            model.addAttribute("error", question);
            return "html/error";
        }
        model.addAttribute("question", question);
        model.addAttribute("category", new Category(category));
        return "html/question";
    }
}
