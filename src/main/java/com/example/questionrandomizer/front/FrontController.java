package com.example.questionrandomizer.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}
