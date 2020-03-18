package com.company;

import java.util.*;

public class Matching {

    Map<Resident, Hospital> residentMatch;
    Map<Hospital, Set<Resident>> hospitalMatch;

    Matching(){
        residentMatch = new HashMap<>();
        hospitalMatch = new HashMap<>();
    }

    Matching(Problem p){
        residentMatch = new HashMap<>();
        hospitalMatch = new HashMap<>();
        for(var h: p.hospitals){
            hospitalMatch.put(h,new HashSet<>());
        }
    }

    public void setMatch(Resident resident, Hospital hospital){
        residentMatch.put(resident,hospital);
        if (!hospitalMatch.containsKey(hospital)) {
            hospitalMatch.put(hospital, new TreeSet<>());
        }
        hospitalMatch.get(hospital).add(resident);
    }

    public void removeMatch(Resident resident, Hospital hospital){
        if(!residentMatch.containsKey(resident)) return;
        if(!hospitalMatch.containsKey(hospital)) return;
        residentMatch.remove(resident);
        hospitalMatch.get(hospital).remove(resident);
    }


    public boolean isValid(){

        for (var hospital: hospitalMatch.keySet()){
            if(hospital.getCapacity() < hospitalMatch.get(hospital).size()) {
                System.err.println(hospital + " has violated capacity");
                return false;
            }
            for (var resident: hospitalMatch.get(hospital)){
                if(!residentMatch.get(resident).equals(hospital)) {
                    System.err.println(resident + ": " + residentMatch.get(resident) + " is not " + hospital);
                    return false;
                }
            }
        }

        return true;

    }

    int getSize(){return residentMatch.size();}

    @Override
    public String toString() {
        return "Matching{" +
                "residentMatch = " + residentMatch +
                "}";
    }
}
