package com.example.uw_rate_em;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    //course constructor
    String Name;
    double gpa;
    int ratings;
    static ArrayList<Course> courseList = new ArrayList<Course>();

    public Course (String Name_in)
    {
        Name = Name_in;
        gpa = 0;
        ratings = 0;
    }
    public String getName(){
        return this.Name;
    }
    public double getGpa(){
        return this.gpa;
    }
    public void setGpa( double gpa_in){
        gpa = gpa_in;
    }
    public double addGrade(double grade_in){
        double temp = this.ratings * this.gpa;
        temp += grade_in;
        this.ratings++;
        this.gpa = (temp / this.ratings);
        return this.gpa;
    }
    // add a course to the courseList
    // returns whether class was added
    static public boolean addCourse(Course course){
        if ((searchCourse(course.getName()) != null )){
            return false;
        }
        courseList.add(course);
        return true;
    }

    // search for a course from the courseList
    // returns whether class was found
    static public Course searchCourse( String courseName){
        for(Course d : courseList){
            if(d.Name != null && d.Name.equals(courseName)) {
                return d;
            }
        }
        return null;
    }


}
