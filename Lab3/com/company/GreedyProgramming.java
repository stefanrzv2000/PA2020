package com.company;

import java.util.Vector;

public class GreedyProgramming implements Algorithm{
    @Override
    public Knapsack solve(Problem problem) {
        var items = new Vector<>(problem.getItems());
        items.sort((c1,c2)->(c1.profitFactor()<=c2.profitFactor() ? 0 : -1));
        double currentWeight = 0;
        Knapsack knapsack = new Knapsack(problem.getKnapsack().getCapacity());
        for(var item : items){
            if(currentWeight + item.getWeight() > knapsack.getCapacity()) continue;
            knapsack.addItem(item);
            currentWeight = currentWeight + item.getWeight();
        }
        knapsack.setFull();

        return knapsack;
    }
}
