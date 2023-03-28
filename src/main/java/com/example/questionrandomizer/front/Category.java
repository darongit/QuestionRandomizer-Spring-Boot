package com.example.questionrandomizer.front;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Category {
    private String name;
    private String urlPath;
    public Category(String name) {
        this.name = name;
        this.urlPath = "/" + name.toLowerCase().replace(" ", "+");
    }
    public static Category getAll() {
        Category result = new Category("ALL CATEGORIES");
        result.urlPath = "/all+categories";
        return result;
    }
}
