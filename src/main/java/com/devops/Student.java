package com.devops;

public class Student {
    private String studentName;
    private String rollNumber;
    private String email;
    private String course;

    public Student() {
    }

    public Student(String studentName, String rollNumber, String email, String course) {
        this.studentName = studentName;
        this.rollNumber = rollNumber;
        this.email = email;
        this.course = course;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public boolean isValid() {
        return studentName != null && !studentName.trim().isEmpty() &&
               rollNumber != null && !rollNumber.trim().isEmpty() &&
               email != null && email.contains("@") &&
               course != null && !course.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentName='" + studentName + '\'' +
                ", rollNumber='" + rollNumber + '\'' +
                ", email='" + email + '\'' +
                ", course='" + course + '\'' +
                '}';
    }
}
