package com.example.sofit.model;

public class Serie {
    private int reps;
    private int weight;

    public Serie(int reps, int weight) {
        this.reps = reps;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getReps() {
        return reps;
    }
}
