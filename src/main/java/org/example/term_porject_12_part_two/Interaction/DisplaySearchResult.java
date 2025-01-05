package org.example.term_porject_12_part_two.Interaction;

import org.example.term_porject_12_part_two.Data.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class DisplaySearchResult {

    public static void displayPlayerQuery(ArrayList<Player> queryResult, int ii){
        if(queryResult.isEmpty()){
            switch (ii){
                case 1:
                    System.out.println("No such player with this name");
                    break;
                case 2:
                    System.out.println("No such player with this country and club");
                    break;
                case 3:
                    System.out.println("No such player with this position");
                    break;
                case 4:
                    System.out.println("No such player with this weekly salary range");
                    break;
            }
            return;
        }
        else{
            System.out.println("There are "+queryResult.size()+" matches.");
            for(int i = 0;i < queryResult.size();i++){
                System.out.println("Match No "+(i+1));
                queryResult.get(i).displayPlayer();
                System.out.println();
            }
        }
    }

    public static void displayCountryWiseCount(HashMap<String, Long> countrryWiseCount){
        for(String key: countrryWiseCount.keySet()){
            System.out.println("Country Name: "+key+" Player Count "+countrryWiseCount.get(key));
        }
    }

    public static void displayClubQuery(ArrayList<Player> queryResult){
        if(queryResult.isEmpty()){
            System.out.println("No such club with this name");
            return;
        }
        else{
            System.out.println("There are "+queryResult.size()+" matches.");
            for(int i = 0;i < queryResult.size();i++){
                System.out.println("Match No "+(i+1));
                queryResult.get(i).displayPlayer();
                System.out.println();
            }
        }
    }

    public static void displayTotalSalary(String name, Long totalSalary){
        if(totalSalary == 0L) {
            System.out.println("No such club with this name");
            return;
        }
        System.out.println("Total weekly salary of club " + name + " is " + totalSalary);
    }

}
