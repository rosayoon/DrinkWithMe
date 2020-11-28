package com.example.drinkwithme;

public class SelectedDrinks {
    String name;
    String key;
    String path;
    String imgFile_name;
    String amount;
    public SelectedDrinks(String name,String key, String path, String imgFile_name, String amount){
        this.name= name;
        this.path = path;
        this.key = key;
        this.imgFile_name =imgFile_name;
        this.amount = amount;
    }
}
