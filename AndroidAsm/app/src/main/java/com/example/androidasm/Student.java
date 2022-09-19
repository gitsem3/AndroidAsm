package com.example.androidasm;

public class Student {
    int id;
    String name;
    String email;
    String tel;

    public Student() {
        super();
    }
    public Student(int i, String name,String email , String tel) {
        super();
        this.id = i;
        this.name = name;
        this.email=email;
        this.tel=tel;
    }
    public Student(String name,String email , String tel){
        this.name = name;
        this.email=email;
        this.tel=tel;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
