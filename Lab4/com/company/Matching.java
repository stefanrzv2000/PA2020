package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Matching {

    Map<Resident, Hospital> residentMatch;
    Map<Hospital, Set<Resident>> hospitalMatch;

    Matching(){
        residentMatch = new HashMap<>();
        hospitalMatch = new HashMap<>();
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
            if(hospital.getCapacity() < hospitalMatch.get(hospital).size()) return false;
            for (var resident: hospitalMatch.get(hospital)){
                if(!residentMatch.get(resident).equals(hospital)) return false;
            }
        }

        return true;

    }

}
