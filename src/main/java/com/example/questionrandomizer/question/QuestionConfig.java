package com.example.questionrandomizer.question;

import com.example.questionrandomizer.QuestionRandomizerApplication;
import org.aspectj.apache.bcel.classfile.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Configuration
public class QuestionConfig implements CommandLineRunner {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void run(String... args) throws Exception {
        Path path = Paths.get("src/main/resources/static/questionsFileNames.txt");
        if (!path.toFile().exists()) {
            throw new IllegalStateException("Can't find questions files list file.");
        }
        List<String> questionsPaths = new ArrayList<>();
        String tmp;

        // load paths to questions files
        File file = new File(path.toString());
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            tmp = scanner.nextLine().strip();
            if (!(tmp.startsWith("//") || tmp.equals(""))) {
                questionsPaths.add(String.format("src/main/resources/static/questions/%s", tmp));
            }
        }

        // load questions from questions files
        for (String questionsPath : questionsPaths) {
            path = Paths.get(questionsPath);
            if (!path.toFile().exists()) {
                throw new IllegalStateException("Can't find questions list file.");
            }
            /* get category name from file name.
            "_" will be replaced by " " and ".txt" will be removed.
            category will be changed to uper case.
             */
            String category = path.toString()
                    .split("/")[path.toString().split("/").length-1]
                    .toUpperCase()
                    .replace("_", " ");
            if (category.endsWith(".TXT")) { category = category.substring(0, category.length()-4); }

            file = new File(path.toString());
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                tmp = scanner.nextLine().strip();
                if (!(tmp.startsWith("//") || tmp.equals(""))) {
                    questionRepository.save(new Question(category, tmp));
                }
            }

        }
    }
}
