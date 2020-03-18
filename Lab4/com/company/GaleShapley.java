package com.company;

import java.util.*;
import java.util.function.Function;

public class GaleShapley implements Algorithm {
    @Override
    public Matching solve(Problem p) {
        var matching = new Matching(p);

        Map<Resident, List<Set<Hospital>>> residentPriority = new TreeMap<>();
        for(var resident: p.residentPreferences.keySet()){
            residentPriority.put(resident,new ArrayList<>());
            for(var s: p.residentPreferences.get(resident)){
                residentPriority.get(resident).add(new HashSet<>(s));
            }
        }

        Map<Resident, Hospital> temp = new HashMap<>();
        Map<Resident, Boolean> engaged = new HashMap<>();
        Map<Hospital, List<Resident>> proposals = new HashMap<>();
        Map<Hospital, List<Resident>> accept = new HashMap<>();

        Map<Hospital, Map<Resident,Integer>> HRPriorities = new HashMap<>();
        Map<Resident, Map<Hospital,Integer>> RHPriorities = new HashMap<>();
        //Map<Hospital, Integer> allocated = new HashMap<>();

        for(var resident: p.residents){
            temp.put(resident,null);
            engaged.put(resident,false);
            RHPriorities.put(resident,new HashMap<>());
            int index = 0;
            for(var s: p.residentPreferences.get(resident)){
                for(var h: s){
                    RHPriorities.get(resident).put(h,index);
                }
                index++;
            }
            //engaged.put(resident,false);
        }

        for(var hospital: p.hospitals){
            accept.put(hospital,new ArrayList<>());
            proposals.put(hospital,new ArrayList<>());
            HRPriorities.put(hospital,new HashMap<>());
            int index = 0;
            for(var s: p.hospitalPreferences.get(hospital)){
                for(var r: s){
                    HRPriorities.get(hospital).put(r,index);
                }
                index++;
            }
            //System.out.println(hospital + " priorities: " + HRPriorities.get(hospital));
        }

        Function<Resident,Hospital> proposeNextHospital = (Resident resident) -> {
            Hospital hospital = null;
            for(var h: residentPriority.get(resident).get(0)){
                hospital = h;
                residentPriority.get(resident).get(0).remove(h);
                if(residentPriority.get(resident).get(0).isEmpty()){
                    residentPriority.get(resident).remove(0);
                }
                break;
            }
            proposals.get(hospital).add(resident);
            temp.put(resident,hospital);
            return hospital;
        };

        Function<Hospital,Boolean> acceptProposals = (Hospital hospital) ->{

            var oldAccept = accept.get(hospital);
            var priorities = HRPriorities.get(hospital);
            var prop = Arrays.asList(proposals.get(hospital).stream()
                    .filter(priorities::containsKey).toArray(Resident[]::new));
            proposals.put(hospital,new ArrayList<>());

            //System.out.println(hospital + " priorities: " + priorities);
            prop.sort(Comparator.comparing(priorities::get));
            //System.out.println(hospital + " sorted: " + prop);
            oldAccept.sort(Comparator.comparingInt(priorities::get));

            accept.put(hospital,new ArrayList<>());
            int i = 0, j = 0, space = hospital.getCapacity();

            while(i < prop.size() & j < oldAccept.size() & space > 0){
                if(priorities.get(prop.get(i)) < priorities.get(oldAccept.get(j))){
                    assert temp.get(prop.get(i)).equals(hospital);
                    engaged.put(prop.get(i),true);
                    accept.get(hospital).add(prop.get(i));
                    //System.out.print('!');
                    i++;
                }
                else{
                    assert engaged.get(oldAccept.get(j));
                    accept.get(hospital).add(oldAccept.get(j));
                    //System.out.print('#');
                    j++;
                }
                space--;
            }
            for(;i<prop.size() & space > 0;i++) {
                assert temp.get(prop.get(i)).equals(hospital);
                engaged.put(prop.get(i),true);
                accept.get(hospital).add(prop.get(i));
                //System.out.print('$');
                space--;
            }
            for(;j<oldAccept.size() & space > 0;j++){
                assert engaged.get(oldAccept.get(j));
                accept.get(hospital).add(oldAccept.get(j));
                //System.out.print('&');
                space--;
            }
            if(space == 0){
                for(;i<prop.size();i++) {
                    assert !engaged.get(prop.get(i));
                }
                for(;j<oldAccept.size();j++){
                    engaged.put(oldAccept.get(j),false);
                }
            }
            //System.out.println();

            return accept.get(hospital).size() == hospital.getCapacity();
        };

        boolean ok = false;
        int round = 0;

        while (!ok) {
            ok = true;
            round++;
            //System.out.println("\nRound " + round + ":\n");
            for (var resident : p.residents) {
                if (engaged.get(resident)) continue;
                if (residentPriority.get(resident).isEmpty()) continue;
                Hospital hospital = proposeNextHospital.apply(resident);
                //System.out.print(resident + " : " + hospital + ";  ");
                ok = false;
            }
            //System.out.println();
            //System.out.println();



            for (var hospital : p.hospitals) {
                if(proposals.get(hospital).isEmpty()) continue;
                //System.out.println(hospital + " capacity: " + hospital.getCapacity());
                //System.out.println(hospital + " proposals: " + proposals.get(hospital));
                acceptProposals.apply(hospital);
                //System.out.println(hospital + " accepted: " + accept.get(hospital));
                //System.out.println();
            }
        }

        for (Resident resident: temp.keySet()){
            if(engaged.get(resident)) {
                matching.setMatch(resident,temp.get(resident));
            }
        }
        //assert p.isStable(matching);
        return matching;
    }
}
