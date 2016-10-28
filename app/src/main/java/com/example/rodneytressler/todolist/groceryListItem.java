package com.example.rodneytressler.todolist;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class groceryListItem implements Comparable<groceryListItem>{
    @SerializedName("title")
    private String title;

    @SerializedName("text")
    private String text;

    @SerializedName("date")
    private String date;

    @SerializedName("category")
    private String category;

    @SerializedName("currentDate")
    private Date currentDate;

    @SerializedName("day")
    private String day;

    @SerializedName("month")
    private String month;

    @SerializedName("time")
    private String time;

    public groceryListItem(String title, String text, String date, String category, Date currentDate, String day, String month, String time) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.category = category;
        this.currentDate = currentDate;
        this.day = day;
        this.month = month;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int compareTo(groceryListItem another) {
//        return another.getDate().compareTo(getDate()); // puts newest on top
        return getCategory().compareTo(another.getCategory()); //puts newest on bottom
    }

}
