package com.example.questionrandomizer.question;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class QuestionController {
    private QuestionService questionService;
    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String mainPage() {
        return questionService.mainPage();
    }
    @GetMapping("/question/all")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/question/{questionId}")
    public Question getQuestionById(@PathVariable Long questionId) {
        return questionService.getQuestionById(questionId);
    }

    @PostMapping("/question/add")
    public Question addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/question/delete/{questionId}")
    public Question deleteQuestion(@PathVariable Long questionId) {
        return questionService.deleteQuestion(questionId);
    }

    @PutMapping("/question/put/{questionId}")
    public Question putQuestion(@RequestBody Question question, @PathVariable Long questionId) {
        return questionService.putQuestion(question, questionId);
    }

    @GetMapping("/question/random")
    public Question getRandomQuestion() {
        return questionService.getRandomQuestion();
    }

    @GetMapping("/question/random/{category}")
    public Question getRandomQuestion(@PathVariable String category) {
        return questionService.getRandomQuestionByCategory(category.toLowerCase());
    }

    @GetMapping("/question/reset")
    public void resetQuestions() {
        questionService.resetQuestions();
    }

    @GetMapping("/question/reset/{category}")
    public void resetQuestionsByCategory(@PathVariable String category) {
        questionService.resetQuestionsByCategory(category.toLowerCase());
    }
}
