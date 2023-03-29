package com.example.questionrandomizer.front;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public String getToReset() {
        return String.format("/settings/reset/%s", name.toLowerCase().replace(" ", "+"));
    }

}
