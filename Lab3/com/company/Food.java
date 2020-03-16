package com.company;

public class Food implements Item{

    private String name;
    private double weight;

    public Food(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }


    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getValue() {
        return 2*weight;
    }


}
