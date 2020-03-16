package com.company;

import java.util.Arrays;

public class DynamicProgramming implements Algorithm{
    @Override
    public Knapsack solve(Problem problem) {
        Knapsack knapsack = new Knapsack(problem.getKnapsack().getCapacity());
        if(!knapsack.isEmpty()) return knapsack;

        var items = problem.getItems();
        //items.sort((c1,c2)->(c1.profitFactor()<=c2.profitFactor() ? 0 : -1));

        int n = problem.getItems().size();
        int capacity = (int)problem.getKnapsack().getCapacity()+1;
        double[][] dp = new double[n+1][capacity];
        int [][] last = new int[n+1][capacity];
        for(int w = 0; w < capacity; w++){
            dp[0][w] = 0;
            last[0][w] = -1;
        }
        for(int i = 1; i <= n; i++){
            for(int w = 0; w < capacity; w++){
                int wi = (int)items.get(i-1).getWeight();
                double vi = items.get(i-1).getValue();
                if(wi > w){
                    dp[i][w] = dp[i-1][w];
                    last[i][w] = last[i-1][w];
                }
                else{
                    //dp[i][w] = Math.max(dp[i-1][w], dp[i-1][w-wi] + vi);
                    if(dp[i-1][w] > dp[i-1][w-wi] + vi){
                        dp[i][w] = dp[i-1][w];
                        last[i][w] = last[i-1][w];
                    }
                    else{
                        dp[i][w] = dp[i-1][w-wi] + vi;
                        last[i][w] = i-1;
                    }
                }
            }
            //System.out.println(Arrays.toString(dp[i]));
        }

        //System.out.println();

        /*
        for(int i = 1; i <= n; i++){
            //System.out.println(Arrays.toString(last[i]));
        }
        */

        //double wMax = dp[n-1][capacity-1];

        int w = capacity-1;
        int i = last[n][w];

        //System.out.println(w + " " + i);

        while(i >= 0){
            knapsack.addItem(items.get(i));
            int wi = (int)items.get(i).getWeight();
            w = w - wi;
            i = last[i][w];
        }

        knapsack.setFull();
        return knapsack;
    }

}
