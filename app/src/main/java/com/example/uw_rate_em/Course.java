package com.example.uw_rate_em;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    //course constructor
    public String Name;
    public double gpa;
    public int ratings;

    public Course(String name) {
        Name = name;
        gpa = 0;
        ratings = 0;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }
}
