package com.company;

import java.util.Arrays;
import java.util.Objects;

class Vehicle {
    boolean asignat;
}

public class Depot {

    private String name;
    private Integer vehicleNumber=0;
    //private static Integer vehicleTotalNumber=0;
    private Vehicle[] vehiclesList;
    // private static Vehicle[] totalVehiclesList;


    public Depot(String name) {
        vehiclesList =new Vehicle[2000];
        //Vehicle[] totalVehiclesList=new Vehicle[2000];
        this.name = name;
        this.vehicleNumber = 0;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addVehicle(Vehicle car1){

        int ok=1;

        ///verificare daca masina nu apartine deja depozitului

        for(int i=1;i<=vehicleNumber;i++)
            if(vehiclesList[i]==car1)
            {
                System.out.println("VEHICULUL EXISTA DEJA IN DEPOZIT");
                ok=0;
            }

        ////verificare daca masina nu apartine altui depozit
        if(ok==1)
            if(car1.asignat)
            {
                System.out.println("VEHICULUL EXISTA DEJA IN ALT DEPOZIT");
                ok=0;
            }

        if(ok==1)
        {
            vehicleNumber++;
            // vehicleTotalNumber++;
            this.vehiclesList[vehicleNumber]=car1;
            //this.totalVehiclesList[vehicleTotalNumber]=car1;
            car1.asignat=true;
        }



    }



    @Override
    public String toString() {
        StringBuilder mesaj=new StringBuilder();

        mesaj.append("Depot "+name+":");

        for(int i=1;i<=vehicleNumber;i++)
            mesaj.append(vehiclesList[i]);


        return mesaj.toString();
    }

    public Integer getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(Integer vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Vehicle[] getVehiclesList() {
        return vehiclesList;
    }

    public void setVehiclesList(Vehicle[] vehiclesList) {
        this.vehiclesList = vehiclesList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Depot depot = (Depot) o;
        return Objects.equals(name, depot.name);// &&
        //  Objects.equals(vehicleNumber, depot.vehicleNumber) &&
        //  Arrays.equals(vehiclesList, depot.vehiclesList);
    }



    // public Integer getVehicleTotalNumber() {        return vehicleTotalNumber;    }

    //public Vehicle[] getTotalVehiclesList() {        return totalVehiclesList;    }
}