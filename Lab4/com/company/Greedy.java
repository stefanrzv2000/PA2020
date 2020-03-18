package com.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Greedy implements Algorithm {
    @Override
    public Matching solve(Problem p) {
        var matching = new Matching(p);
        Map<Hospital, Integer> allocated = new HashMap<>();
        Map<Hospital, Set<Resident>> accepted = new HashMap<>();

        for(var hospital: p.hospitals){
            allocated.put(hospital,0);
            accepted.put(hospital,new HashSet<>());
            for(var s: p.hospitalPreferences.get(hospital)){
                for (var resident: s){
                    accepted.get(hospital).add(resident);
                }
            }
        }

        for(var resident: p.residents){
            boolean ok = false;
            for(var s: p.residentPreferences.get(resident)) {
                for (var hospital : s) {
                    if (accepted.get(hospital).contains(resident) & allocated.get(hospital) < hospital.getCapacity()) {
                        matching.setMatch(resident, hospital);
                        allocated.put(hospital, allocated.get(hospital) + 1);
                        ok = true;
                        break;
                    }
                }
                if (ok) break;
            }
        }

        return matching;
    }
}
