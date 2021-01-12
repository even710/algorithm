package com.even.exercise.hashtable;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/4/8 16:08
 */
public class LieTou {
    public int id;
    public int score;
    public LieTou next;
    public LieTou down;
    public LieTou hhnext;//拉链
    public LieTou prev;

    public LieTou(int id, int score) {
        this.id = id;
        this.score = score;
    }

    public LieTou(int score) {
        this.score = score;
    }
}
