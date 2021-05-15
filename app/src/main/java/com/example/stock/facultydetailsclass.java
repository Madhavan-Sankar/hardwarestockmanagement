package com.example.stock;

public class facultydetailsclass {
    String faculty_id,faculty_name,email,password,dept;

    public facultydetailsclass(String faculty_id, String faculty_name, String email, String password, String dept) {
        this.faculty_id = faculty_id;
        this.faculty_name = faculty_name;
        this.email = email;
        this.password = password;
        this.dept = dept;
    }

    public String getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(String faculty_id) {
        this.faculty_id = faculty_id;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
