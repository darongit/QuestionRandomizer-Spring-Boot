package com.example.questionrandomizer.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

@Configuration
public class QuestionConfig implements CommandLineRunner {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void run(String... args) throws Exception {
//        addQuestionsFromFiles2();
        addQuestionsFromFiles();
//        addQuestionsFromClass();
    }

    private void addQuestionsFromFiles2() {
        URL fileNamesPath = QuestionConfig.class.getClassLoader().getResource("static/questionsFileNames.txt");
        String tmp = null;
        String tmpCategory = null;
        File file = null;
        Scanner scanner = null;
        List<String> filePaths = new ArrayList<>();

        try {
            file = new File(fileNamesPath.toURI());
        } catch (URISyntaxException e) {
            file = new File(fileNamesPath.getPath());
        }

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("Problem with %s", file.getAbsolutePath()));
        }

        while (scanner.hasNextLine()) {
            tmp = scanner.nextLine().strip();
            if (tmp.isBlank() || tmp.startsWith("//")) {
                continue;
            }
            filePaths.add(String.format("static/questions/%s", tmp));
            System.out.println(filePaths.get(filePaths.size() - 1));
        }

        for (String path: filePaths) {
            try {
                file = new File(QuestionConfig.class.getClassLoader().getResource(path).toURI());
                scanner = new Scanner(file);
            } catch (URISyntaxException | FileNotFoundException e) {
                throw new RuntimeException(String.format("Problem with %s", path));
            }
            // take back file name to create category name
            tmpCategory = path.split("/")[path.split("/").length-1]
                    .toUpperCase()
                    .replace(".TXT", "")
                    .strip()
                    .replace("_", " ")
                    .replace("+", " ");

            while (scanner.hasNextLine()) {
                tmp = scanner.nextLine().strip();
                if (tmp.isBlank() || tmp.startsWith("\\")) {
                    continue;
                }
                questionRepository.save(new Question(tmpCategory, tmp));
            }
        }
    }

    private void addQuestionsFromFiles() {
        URL fileNamesPath = QuestionConfig.class.getClassLoader().getResource("static/questionsFileNames.txt");
        String tmp = null;
        String tmpCategory = null;
        File file = null;
        Scanner scanner = null;
        List<String> filePaths = new ArrayList<>();

        try {
            file = new File(fileNamesPath.toURI());
        } catch (URISyntaxException e) {
            file = new File(fileNamesPath.getPath());
        }

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("Problem with %s", file.getAbsolutePath()));
        }

        while (scanner.hasNextLine()) {
            tmp = scanner.nextLine().strip();
            if (tmp.isBlank() || tmp.startsWith("//")) {
                continue;
            }
            filePaths.add(String.format("static/questions/%s", tmp));
            System.out.println(filePaths.get(filePaths.size() - 1));
        }

        for (String path: filePaths) {
            try {
                file = new File(QuestionConfig.class.getClassLoader().getResource(path).toURI());
                scanner = new Scanner(file);
            } catch (URISyntaxException | FileNotFoundException e) {
                throw new RuntimeException(String.format("Problem with %s", path));
            }
            // take back file name to create category name
            tmpCategory = path.split("/")[path.split("/").length-1]
                    .toUpperCase()
                    .replace(".TXT", "")
                    .strip()
                    .replace("_", " ")
                    .replace("+", " ");

            while (scanner.hasNextLine()) {
                tmp = scanner.nextLine().strip();
                if (tmp.isBlank() || tmp.startsWith("\\")) {
                    continue;
                }
                questionRepository.save(new Question(tmpCategory, tmp));
            }
        }
    }
    public void addQuestionsFromClass() {
        Map<String, String> map = new HashMap<>();
        map.put("FUTURE", QuestionFilesToString.future());
        map.put("MUSIC", QuestionFilesToString.music());
        map.put("ICE BREAK", QuestionFilesToString.ice_break());

        for (String category: map.keySet()) {
            for (String content: map.get(category).split("\n")) {
                if (content.isBlank()) {
                    continue;
                }
                questionRepository.save(new Question(category, content.strip()));
            }
        }
    }
}
