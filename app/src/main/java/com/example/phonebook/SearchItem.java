package com.example.phonebook;

public class SearchItem {
    private String name;
    private String number;
    public SearchItem(String name, String number){
        this.name = name;
        this.number = number;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setNumber(String number){
        this.number = number;
    }
    public String getName(){
        return this.name;
    }
    public String getNumber(){
        return  this.number;
    }
}
