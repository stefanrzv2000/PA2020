package com.company;

public class Book implements Item{
    private String name;
    private int pageNumber;
    private double pageWeight;
    private double value;

    public Book(String name, int pageNumber, double value) {

        this.name = name;
        this.pageNumber = pageNumber;
        this.value = value;
        this.pageWeight = 0.01f;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public double getWeight() {
        return pageNumber /100;
    }
}
