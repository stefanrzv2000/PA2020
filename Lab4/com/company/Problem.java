package com.company;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import com.github.javafaker.*;

public class Problem {

    private static double p = 0.3;

    List<Hospital> hospitals;
    List<Resident> residents;

    Map<Hospital, List<Set<Resident>>> hospitalPreferences = new TreeMap<>();
    Map<Resident, List<Set<Hospital>>> residentPreferences = new TreeMap<>();
    //Map<Hospital, Resident> match;

    Problem(int hospitalsNo, int residentsNo){
        Faker faker = new Faker();
        residents = Arrays.asList(IntStream.range(0,residentsNo)
                .mapToObj(i -> new Resident(faker.name().fullName()))
                .toArray(Resident[]::new)
        );
        hospitals = Arrays.asList(IntStream.range(0,hospitalsNo)
                .mapToObj(i -> new Hospital(faker.address().cityName() + " Hospital", (int)(2*Math.random()*residentsNo/hospitalsNo + 1) ))
                .toArray(Hospital[]::new)
        );

        for(var resident: residents){
            residentPreferences.put(resident,new ArrayList<>());
            int preferencesNo = (int)(Math.random()*hospitalsNo/2 + hospitalsNo/4 + 1);
            AtomicInteger currentIndex = new AtomicInteger(0);
            residentPreferences.get(resident).add(new HashSet<>());

            IntStream.range(0,preferencesNo)
                    .mapToObj(i -> hospitals.get((int) (Math.random() * hospitalsNo)))
                    .forEach(h -> {
                        if(!residentPrefersHospital(resident,h)) {
                            if(Math.random() > p & !residentPreferences.get(resident).get(currentIndex.get()).isEmpty()) {
                                residentPreferences.get(resident).add(new HashSet<>());
                                currentIndex.getAndIncrement();
                            }
                            residentPreferences.get(resident).get(currentIndex.get()).add(h);

                        }
                    });
        }

        for(var hospital: hospitals){
            hospitalPreferences.put(hospital,new ArrayList<>());
            int preferencesNo = (int)(Math.random()*residentsNo/2 + residentsNo/6 + 1);
            AtomicInteger currentIndex = new AtomicInteger();
            hospitalPreferences.get(hospital).add(new HashSet<>());

            IntStream.range(0,preferencesNo)
                    .mapToObj(i -> residents.get((int)(Math.random() * residentsNo)))
                    .forEach( r ->{
                        if(!hospitalPrefersResident(hospital,r)){
                            if(Math.random() > p & !hospitalPreferences.get(hospital).get(currentIndex.get()).isEmpty()){
                                hospitalPreferences.get(hospital).add(new HashSet<>());
                                currentIndex.getAndIncrement();
                            }
                            hospitalPreferences.get(hospital).get(currentIndex.get()).add(r);
                        }
                    });

        }

    }

    private boolean residentPrefersHospital(Resident resident, Hospital hospital){
        for(var s: residentPreferences.get(resident)){
            if ( s.contains(hospital) ) return true;
        }
        return false;
    }

    private boolean hospitalPrefersResident(Hospital hospital, Resident resident){
        for(var s: hospitalPreferences.get(hospital)){
            if (s.contains(resident)) return true;
        }
        return false;
    }

    public boolean isStable(Matching matching){
        if(!matching.isValid()) return false;

        Map<Hospital, Map<Resident,Integer>> HRPriorities = new HashMap<>();
        for(var hospital: hospitals){
            //accept.put(hospital,new ArrayList<>());
            //proposals.put(hospital,new ArrayList<>());
            HRPriorities.put(hospital,new HashMap<>());
            int index = 0;
            for(var s: hospitalPreferences.get(hospital)){
                for(var r: s){
                    HRPriorities.get(hospital).put(r,index);
                }
                index++;
            }
            //System.out.println(hospital + " priorities: " + HRPriorities.get(hospital));
        }

        Map<Hospital, Set<Resident>> accepted = new HashMap<>();
        for(var hospital: hospitals){
            accepted.put(hospital,new HashSet<>());
            for(var s: hospitalPreferences.get(hospital)) {
                for (var resident: s){
                    accepted.get(hospital).add(resident);
                }
            }
        }

        for (var resident: matching.residentMatch.keySet()){
            var hospital = matching.residentMatch.get(resident);

            for (var s: residentPreferences.get(resident)) {
                if(s.contains(hospital)) break;
                for (var potentialHospital : s) {

                    if(!accepted.get(potentialHospital).contains(resident)) continue;


                    if (matching.hospitalMatch.get(potentialHospital).size() < potentialHospital.getCapacity()) {
                        System.err.println("The pair " + resident + " :: " + hospital + " is not stable because of " + potentialHospital);
                        System.err.println( matching.hospitalMatch.get(potentialHospital).size()  + " < " + potentialHospital.getCapacity());
                        System.err.flush();
                        return false;
                    }


                    //int size = hospitalPreferences.get(potentialHospital).size();
                    int currentIndex = HRPriorities.get(potentialHospital).get(resident);
                    for(Resident other : matching.hospitalMatch.get(potentialHospital)) {
                        int otherIndex = HRPriorities.get(potentialHospital).get(other);
                        if(currentIndex < otherIndex){
                            System.out.println();
                            System.out.println("!!!\tThe pair " + resident + " :: " + hospital + " is not stable because of " + potentialHospital + " and " + other);
                            System.out.println();
                            return false;
                        }
                    }
                    /*
                    if (hospitalPrefersResident(potentialHospital, resident)
                            & !hospitalPreferences.get(potentialHospital).get(size - 1).contains(resident))
                    {
                        System.err.println("The pair " + resident + " :: " + hospital + " is not stable because of " + potentialHospital);
                        System.err.flush();
                        return false;
                    }

                     */
                }
            }
        }

        return true;
    }

    public int score(Matching matching){
        if(!matching.isValid()) return -1;

        int score = 0;

        for(var resident: residents){
            var hospital = matching.residentMatch.get(resident);
            if(hospital == null){
                score = score + residentPreferences.get(resident).size()*2;
                continue;
            }
            for(var s: residentPreferences.get(resident)){
                score = score + 1;
                if(s.contains(hospital)) break;
            }
        }
        return score;
    }

    public int totalCapacity(){
        int cap = 0;
        for(var hospital: hospitals){
            cap = cap + hospital.getCapacity();
        }
        return cap;
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
