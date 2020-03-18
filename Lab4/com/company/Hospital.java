package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Hospital implements Comparable<Hospital>{
    private String name;
    private int capacity;
    //private List<Resident> residents;

    Hospital(String name){
        this.name = name;
        //residents = new ArrayList<>();
    }

    Hospital(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
    }

/*
    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(Resident ... residents) {
        this.residents = Arrays.asList(residents);
    }
*/

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    @Override
    public int compareTo(Hospital hospital) {
        return this.name.compareTo(hospital.getName());
    }

    @Override
    public String toString() {
        return name + " (" + capacity + ")";
    }
}
