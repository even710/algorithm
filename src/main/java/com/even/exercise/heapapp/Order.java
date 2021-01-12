package com.even.exercise.heapapp;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/4/9 11:03
 */
public class Order {
    private int id;
    private int price;
    private int db;//记录出自哪个数据库

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }

    public Order() {
    }

    public Order(int id, int price, int db) {
        this.id = id;
        this.price = price;
        this.db = db;
    }
}
