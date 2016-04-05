package com.example.administrator.testa.sqlite;

/**
 * Created by wp on 2015/12/23.
 */
public class Person {
    private String name;
    private int age;

    public Person(String mName, int mAge) {
        name = mName;
        age = mAge;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        name = mName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int mAge) {
        age = mAge;
    }
}
