package com.company;

enum WeaponType{
    SWORD, GUN, BOW;

    @Override
    public String toString() {
        switch (this){
            case SWORD: return "Sword";
            case BOW: return "Bow";
            case GUN: return "Gun";
            default: return "Weapon";
        }
    }
}

public class Weapon implements Item{
    private WeaponType type;
    private double weight;
    private double value;

    public Weapon(WeaponType type, double weight, double value) {
        this.type = type;
        this.weight = weight;
        this.value = value;
    }


    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String getName() {
        return type.toString();
    }
}
