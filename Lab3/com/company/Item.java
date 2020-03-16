package com.company;

public interface Item{
    String getName();

    double getValue();

    double getWeight();

    default double profitFactor(){
        return getValue() / getWeight();
    }

    default public String description(){
        return "\t" + getName() + ", " +
                "weight = " + getWeight() +
                ", value = " + getValue() +
                " (profit factor = " + profitFactor() + ")\n";
    }

    static Item getRandom(){
        int t = (int)(Math.random()*3);
        switch (t){
            case 0: return new Book("book" + (int)(Math.random()*1000000),(int)(Math.random()*1000 + 100), (int)(Math.random()*20 + 1));
            case 1: return new Food("food" + (int)(Math.random()*1000000),(int)(Math.random()*20 + 1));
            default: return new Weapon(WeaponType.SWORD, (int)(Math.random()*30 + 1), (int)(Math.random()*20 + 1));
        }
    }

}
