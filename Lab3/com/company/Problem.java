package com.company;

import java.util.ArrayList;
import java.util.List;

public class Problem{
    private Knapsack knapsack;
    private List<Item> items;

    public Problem(int max, int count){
        knapsack = new Knapsack((int)(Math.random()*max));
        items = new ArrayList<>();
        for(int i = 0; i < count; i++){
            items.add(Item.getRandom());
        }
    }

    public Problem(Knapsack knapsack, List<Item> items){
        this.knapsack = knapsack;
        this.items = items;
    }

    public Knapsack getKnapsack() {
        return knapsack;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("capacity of the knapsack = ").append(knapsack.getCapacity());
        sb.append("\navailable items:\n");
        for (var item: items) {
            sb.append(item.description());
        }

        return sb.toString();

    }
}
