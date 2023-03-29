package com.example.questionrandomizer.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FrontController {
    private FrontService frontService;
    @Autowired
    public FrontController(FrontService frontService) {
        this.frontService = frontService;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        return frontService.mainPage(model);
    }

    @GetMapping("/settings/reset")
    public String settingsPage(Model model) {
        return frontService.resetPage(model, "");
    }
    @GetMapping("/settings/reset/{category}")
    public String settingsPage(Model model, @PathVariable String category) {
        return frontService.resetPage(model, category);
    }

    @GetMapping("/{category}")
    public String randomQuestionPageByCategory(Model model, @PathVariable String category) {
        return frontService.randomQuestionPageByCategory(model, category);
    }
}
