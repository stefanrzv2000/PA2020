package com.company;

import java.util.ArrayList;
import java.util.List;

public class Knapsack {
    private double capacity;
    private boolean empty = true;

    private List<Item> itemList = new ArrayList<>();

    public Knapsack(double capacity) {
        this.capacity = capacity;
    }

    public double getCapacity() {
        return capacity;
    }

    public void addItem(Item item) {
        itemList.add(item);
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setFull() {
        empty = false;
    }

    public double totalValue() {
        double value = 0;
        for (var item : itemList) value = value + item.getValue();
        return value;
    }

    public double totalWeight(){
        double value = 0;
        for (var item : itemList) value = value + item.getWeight();
        return value;
    }

    public boolean isEmpty() {
        return empty;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Knapsack:\ncapacity = ").append(capacity).append("\nitems: ");

        for (var item : itemList) {
            sb.append(item.getName()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("\ntotal weight = ").append(totalWeight());
        sb.append("\ntotal value = ").append(totalValue()).append("\n");

        return sb.toString();
    }
}