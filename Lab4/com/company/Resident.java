package com.company;

import java.util.Arrays;
import java.util.List;

public class Resident implements Comparable<Resident>{
    private String name;
    //private List<Hospital> hospitals;


    public Resident(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
/*
    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(Hospital ... hospitals) {
        this.hospitals = Arrays.asList(hospitals);
    }
*/
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Resident resident) {
        return this.name.compareTo(resident.getName());
    }
}
