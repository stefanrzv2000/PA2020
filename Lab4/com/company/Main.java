package com.company;

import java.util.*;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        var residents = IntStream.range(0,4)
                .mapToObj(i -> new Resident("R" + i))
                .toArray(Resident[]::new);

        var hospitals = IntStream.range(0,3)
                .mapToObj(i -> new Hospital("H" + i))
                .toArray(Hospital[]::new);

        hospitals[0].setCapacity(1);
        hospitals[1].setCapacity(2);
        hospitals[2].setCapacity(2);

        List<Resident> residentList = new ArrayList<>(Arrays.asList(residents));
        Set<Hospital> hospitalSet = new TreeSet<>(Arrays.asList(hospitals));

        residentList.sort(Comparator.comparing(Resident::getName));

        Map<Resident,List<Hospital>> residentPreferences = new HashMap<>();
        Map<Hospital,List<Resident>> hospitalPreferences = new TreeMap<>();

        residentPreferences.put(residents[0], new ArrayList<>(Arrays.asList(hospitals)));
        residentPreferences.put(residents[1], new ArrayList<>(Arrays.asList(hospitals)));
        residentPreferences.put(residents[2], new ArrayList<>(Arrays.asList(hospitals[0], hospitals[1])));
        residentPreferences.put(residents[3], new ArrayList<>(Arrays.asList(hospitals[0], hospitals[2])));

        hospitalPreferences.put(hospitals[0], new ArrayList<>(Arrays.asList(residents[3], residents[0], residents[1], residents[2])));
        hospitalPreferences.put(hospitals[1], new ArrayList<>(Arrays.asList(residents[0], residents[2], residents[1])));
        hospitalPreferences.put(hospitals[2], new ArrayList<>(Arrays.asList(residents[0], residents[1], residents[3])));

        System.out.println("Residents Preferences:");
        System.out.println(residentPreferences);
        System.out.println();

        System.out.println("Hospitals Preferences:");
        System.out.println(hospitalPreferences);
        System.out.println();

        System.out.println("Residents that find acceptable hospitals H0 and H2:");

        residentList.stream()
                .filter(res ->
                        residentPreferences.get(res).contains(hospitals[0])
                        & residentPreferences.get(res).contains(hospitals[2]))
                .forEach(System.out::println);

        System.out.println();
        System.out.println("Hospitals that have R0 as their top preference:");

        hospitalSet.stream()
                .filter(hospital ->
                        !hospitalPreferences.get(hospital).isEmpty()
                        & hospitalPreferences.get(hospital).get(0).equals(residents[0]))
                .forEach(System.out::println);

        Problem p = new Problem(30,500);
        System.out.println("\n\n");
        System.out.println(p);
        System.out.println();
        System.out.println("The total capacity of the hospitals is " + p.totalCapacity());
        System.out.println();

        Algorithm greedy = new Greedy();
        Algorithm gs = new GaleShapley();

        Matching greedyMatching = greedy.solve(p);

        System.out.println("\n\nGreedyMatching:\n");
        System.out.println(greedyMatching);
        System.out.println("The greedy matching satisfies " + greedyMatching.getSize() + " residents out of " + p.residents.size());
        System.out.println(greedyMatching.isValid()?"The greedy matching is valid":"the greedy matching is NOT valid");
        System.out.println(p.isStable(greedyMatching)?"The greedy matching is stable":"the greedy matching is NOT stable");
        System.out.println("The greedy matching has a score of " + p.score(greedyMatching));

        //System.out.println("\n\nGSMatching:\n");

        //System.out.println("\n\n");
        //System.out.println(p);
        //System.out.println();

        Matching GSMatching = gs.solve(p);

        System.out.println("\n\nGSMatching:\n");
        System.out.println(GSMatching);
        System.out.println("The GS matching satisfies " + GSMatching.getSize() + " residents out of " + p.residents.size());
        System.out.println(GSMatching.isValid()?"The GS matching is valid":"the GS matching is NOT valid");
        System.out.println(p.isStable(GSMatching)?"The GS matching is stable":"the GS matching is NOT stable");
        System.out.println("The GS matching has a score of " + p.score(GSMatching));


        //System.out.println("\n\n");
        //System.out.println(p);
        //System.out.println();
        //IntStream stream = IntStream.rangeClosed(1,9);
        //stream.parallel().forEach(System.out::println);
    }
}
