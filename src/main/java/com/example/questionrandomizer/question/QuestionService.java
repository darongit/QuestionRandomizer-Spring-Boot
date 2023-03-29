package com.example.questionrandomizer.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;
    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public String mainPage() {
        StringBuilder result = new StringBuilder();
        result.append("<html><body>");

        result.append("/api/v1/question/all -> return list of all questions");
        result.append("<br>");
        result.append("/api/v1/question/category -> return list of all categories");
        result.append("<br>");
        result.append("/api/v1/question/category/{category} -> return list of all questions from given category");
        result.append("<br>");
        result.append("/api/v1/question/{id} -> show question by id");
        result.append("<br>");
        result.append("/api/v1/question/add -> add new question. Use POST with json like {\"category\" : \"categoryName\", \"content\" : \"questions\"}");
        result.append("<br>");
        result.append("/api/v1/question/delete/{id} -> delete question by id");
        result.append("<br>");
        result.append("/api/v1/question/put/{id} -> update question by id. Use PUT with json like {\"category\" : \"categoryName\", \"content\" : \"questions\"}");
        result.append("<br>");
        result.append("/api/v1/question/random -> return random question");
        result.append("<br>");
        result.append("/api/v1/question/random/{category} -> return random question by category");
        result.append("<br>");
        result.append("/api/v1/question/reset -> make every question unseen");
        result.append("<br>");
        result.append("/api/v1/question/reset/{category} -> make every question unseen");

        result.append("</body></html>");
        return result.toString();
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow();
    }

    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question deleteQuestion(Long questionId) {
        Question tmp = questionRepository.findById(questionId).orElseThrow();
        questionRepository.deleteById(questionId);
        return tmp;
    }

    public Question putQuestion(Question question, Long questionId) {
        question.setId(questionId);
        return questionRepository.save(question);
    }

    public Question getRandomQuestion() {
        return getRandomQuestionByCategory("");
    }

    public Question getRandomQuestionByCategory(String category) {
        List<Question> toDraw = null;
        String categoryFromUrl = category.toUpperCase()
                .replace("+", " ")
                .replace("_", " ");

        if (category.equals("") || categoryFromUrl.equals("ALL CATEGORIES")) {
            toDraw = questionRepository.findAll().stream()
                    .filter(question -> !question.isAlreadySeen())
                    .toList();
            if (toDraw.isEmpty() && getAllCategories().contains(categoryFromUrl)) {
                resetQuestions();
                return getRandomQuestionByCategory(categoryFromUrl);
            }
        } else {
            toDraw = questionRepository.findAll().stream()
                    .filter(question -> question.getCategory().equals(categoryFromUrl))
                    .filter(question -> !question.isAlreadySeen())
                    .toList();
            if (toDraw.isEmpty() && getAllCategories().contains(categoryFromUrl)) {
                resetQuestionsByCategory(category);
                return getRandomQuestionByCategory(category);
            }
        }

        int idx = (int)(Math.random()*toDraw.size());
        if (toDraw.size() == 0) {
            System.out.println(category);
        }
        Question result = result = toDraw.get(idx);

        result.setAlreadySeen(true);
        questionRepository.save(result);
        return result;
    }

    public void resetQuestions() {
        resetQuestionsByCategory("");
    }

    public void resetQuestionsByCategory(String category) {
        String categoryFromUrl = category.toUpperCase()
                .replace("+", " ")
                .replace("_", " ");
        List<Question> toChange = new ArrayList<>();
        if (category.equals("") || categoryFromUrl.equals("ALL CATEGORIES")) {
            toChange = questionRepository.findAll();
        } else {
            toChange = questionRepository.findAll().stream()
                    .filter(question -> question.getCategory().equals(categoryFromUrl))
                    .toList();
        }
        for (Question question: toChange) {
            question.setAlreadySeen(false);
            questionRepository.save(question);
        }
    }

    public Set<String> getAllCategories() {
        Set<String> result = new HashSet<>();
        questionRepository.findAll().forEach(question -> result.add(question.getCategory()));
        return result;
    }

    public List<Question> getAllQuestionsFromCategory(String category) {
        String categoryFromUrl = category.replace("+", " ").replace("_", " ");
        return questionRepository.findAll().stream()
                .filter(question -> question.getCategory().equals(categoryFromUrl))
                .toList();
    }
}
