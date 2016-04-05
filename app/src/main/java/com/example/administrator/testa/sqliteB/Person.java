package com.example.administrator.testa.sqliteB;

/**
 * Created by wp on 2015/12/24.
 */
public class Person {
    public int _id;
    public String name;
    public int age;
    public String info;

    public Person() {
    }

    public Person(String name, int age, String info) {
        this.name = name;
        this.age = age;
        this.info = info;
    }
}
