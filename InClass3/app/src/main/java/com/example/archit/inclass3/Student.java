package com.example.archit.inclass3;

import java.io.Serializable;

/**
 * Created by archit on 5/30/17.
 */

public class Student implements Serializable{

    private String name;
    private String email;
    private CharSequence department;
    private int mood;

    public Student(String name, String email, CharSequence department, int mood) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.mood = mood;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CharSequence getDepartment() {
        return department;
    }

    public void setDepartment(CharSequence department) {
        this.department = department;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department=" + department +
                ", mood=" + mood +
                '}';
    }
}
