package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Knapsack knapsack = new Knapsack(10);
        Book[] books = {
                new Book("Dragon Rising",300,5),
                new Book("A Blade in the Dark",400,6)
        };

        Food[] foods = {
                new Food("Cabbage", 2),
                new Food("Rabbit", 5)
        };
        Weapon weapon = new Weapon(WeaponType.SWORD,5,12);

        ArrayList<Item> items = new ArrayList<>();
        items.addAll(Arrays.asList(books));
        items.addAll(Arrays.asList(foods));
        items.add(weapon);

        Problem problem = new Problem(knapsack, items);

        System.out.println(problem);

        Algorithm g = new GreedyProgramming();
        Algorithm d = new DynamicProgramming();

        var kg = g.solve(problem);
        var kd = d.solve(problem);

        System.out.println("Greedy:\n" + kg);
        System.out.println("DP:\n" + kd);


        Problem pbrand = new Problem(200,25);
        System.out.println("Random Problem:\n" + pbrand);

        kg = g.solve(pbrand);
        kd = d.solve(pbrand);

        System.out.println("Greedy:\n" + kg);
        System.out.println("DP:\n" + kd);


    }
}
