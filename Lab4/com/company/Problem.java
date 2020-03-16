package com.company;

import java.util.*;
import java.util.stream.IntStream;

import com.github.javafaker.*;

public class Problem {
    List<Hospital> hospitals;
    List<Resident> residents;

    Map<Hospital, List<Resident>> hospitalPreferences = new TreeMap<>();
    Map<Resident, List<Hospital>> residentPreferences = new TreeMap<>();
    //Map<Hospital, Resident> match;

    Problem(int hospitalsNo, int residentsNo){
        Faker faker = new Faker();
        residents = Arrays.asList(IntStream.range(0,residentsNo)
                .mapToObj(i -> new Resident(faker.name().fullName()))
                .toArray(Resident[]::new)
        );
        hospitals = Arrays.asList(IntStream.range(0,hospitalsNo)
                .mapToObj(i -> new Hospital(faker.address().cityName() + " Hospital", (int)(Math.random()*residentsNo/2) ))
                .toArray(Hospital[]::new)
        );

        for(var resident: residents){
            residentPreferences.put(resident,new ArrayList<>());
            int preferencesNo = (int)(Math.random()*hospitalsNo/2 + hospitalsNo/6 + 1);
            IntStream.range(0,preferencesNo)
                    .mapToObj(i -> hospitals.get((int) (Math.random() * hospitalsNo)))
                    .forEach(h -> {
                        if(!residentPreferences.get(resident).contains(h)) {
                            residentPreferences.get(resident).add(h);
                        }
                    });
        }

        for(var hospital: hospitals){
            hospitalPreferences.put(hospital,new ArrayList<>());
            int preferencesNo = (int)(Math.random()*residentsNo/2 + residentsNo/6 + 1);
            IntStream.range(0,preferencesNo)
                    .mapToObj(i -> residents.get((int)(Math.random() * hospitalsNo)))
                    .forEach( r ->{
                        if(!hospitalPreferences.get(hospital).contains(r)){
                            hospitalPreferences.get(hospital).add(r);
                        }
                    });

        }

    }

    public boolean isStable(Matching matching){
        if(!matching.isValid()) return false;

        for (var resident: matching.residentMatch.keySet()){
            var hospital = matching.residentMatch.get(resident);
            for (var potentialHospital: residentPreferences.get(resident)){
                if(potentialHospital.equals(hospital)) break;
                if()
            }

        }

        return true;
    }

    @Override
    public String toString() {
        return "Problem:" +
                "\n\nHospitals: " + hospitals +
                "\n\nResidents: " + residents +
                "\n\nHospitals Preferences:\n" + hospitalPreferences +
                "\n\nResidents Preferences:\n" + residentPreferences;
    }
}
