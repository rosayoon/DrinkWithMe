package com.example.drinkwithme;


public class Alcohol {
    String name;
    int alcohol_content;
    String key;
    String detail;

    String path;
    String imgFile_name;


    public Alcohol(String name, String key, String path, String imgFile_name, int alcohol_content, String detail) {
        this.name = name;
        this.path =path;
        this.key = key;
        this.imgFile_name =imgFile_name;
        this.alcohol_content = alcohol_content;
        this.detail = detail;
    }

}
