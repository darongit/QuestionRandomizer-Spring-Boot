package com.example.questionrandomizer.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

@Configuration
public class QuestionConfig implements CommandLineRunner {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void run(String... args) throws Exception {
        addQuestions();
    }
    public void addQuestions() {
        // create list of questions questionsFiles to 'questionsFiles' array
        List<File> questionsFiles = new ArrayList<>();
        URL url = Thread.currentThread().getContextClassLoader().getResource("static/questions");
        String path = null;
        try {
            assert url != null;
            path = url.getPath();
        } catch (NullPointerException e) {
            throw new RuntimeException(String.format("%s file is not found", url.getPath()));
        }
        for (File file : new File(path).listFiles()) {
            if (!file.isDirectory() && file.getName().toLowerCase().endsWith(".txt")) {
                questionsFiles.add(file);
            }
        }

        // load questions from questionsFiles
        Scanner scanner = null;
        String tmp;
        String category;
        for (File file : questionsFiles) {
            category = file.getName()
                    .toUpperCase()
                    .replace(".TXT", "")
                    .replace("_", " ")
                    .replace("+", " ");
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(String.format("%s file can't be find", file.getAbsolutePath()));
            }
            while (scanner.hasNextLine()) {
                tmp = scanner.nextLine().strip();
                if (tmp.isBlank() || tmp.startsWith("//")) {
                    continue;
                }
                questionRepository.save(new Question(category, tmp));
            }
        }

    }
}
