package org.example.term_porject_12_part_two.Utility;

import org.example.term_porject_12_part_two.Data.Player;

import java.util.ArrayList;

public class SearchByClub {

    public static int queryType1(ArrayList<Player> playerList, String name, ArrayList<Player> queryResult){
        FileOperations.loadPlayersDataFromFile(playerList);
        long maxSalary = 0L;
        if(name == null) return -1;
        if(name.equals("0")) return 0;
        queryResult.clear();
        for(int i = 0;i < playerList.size();i++){
            if(playerList.get(i).getClub().matches("(?i).*"+name+".*")){
                if(maxSalary < playerList.get(i).getWeeklySalary()) maxSalary = playerList.get(i).getWeeklySalary();
            }
        }
        for(int i = 0;i < playerList.size();i++){
            if(playerList.get(i).getClub().matches("(?i).*"+name+".*") &&
            playerList.get(i).getWeeklySalary() == maxSalary){
                queryResult.add(playerList.get(i));
            }
        }
        return 1;
    }

    public static int queryType2(ArrayList<Player> playerList, String name, ArrayList<Player> queryResult){
        FileOperations.loadPlayersDataFromFile(playerList);
        long maxAge = 0L;
        if(name == null) return -1;
        if(name.equals("0")) return 0;
        queryResult.clear();
        for(int i = 0;i < playerList.size();i++){
            if(playerList.get(i).getClub().matches("(?i).*"+name+".*")){
                if(maxAge < playerList.get(i).getAge()) maxAge = playerList.get(i).getAge();
            }
        }
        for(int i = 0;i < playerList.size();i++){
            if(playerList.get(i).getClub().matches("(?i).*"+name+".*") &&
                    playerList.get(i).getAge() == maxAge){
                queryResult.add(playerList.get(i));
            }
        }
        return 1;
    }

    public static int queryType3(ArrayList<Player> playerList, String name, ArrayList<Player> queryResult){
        FileOperations.loadPlayersDataFromFile(playerList);
        double maxHeight = 0.0;
        if(name == null) return -1;
        if(name.equals("0")) return 0;
        queryResult.clear();
        for(int i = 0;i < playerList.size();i++){
            if(playerList.get(i).getClub().matches("(?i).*"+name+".*")){
                if(maxHeight < playerList.get(i).getHeight()) maxHeight = playerList.get(i).getHeight();
            }
        }
        for(int i = 0;i < playerList.size();i++){
            if(playerList.get(i).getClub().matches("(?i).*"+name+".*") &&
                    playerList.get(i).getHeight() == maxHeight){
                queryResult.add(playerList.get(i));
            }
        }
        return 1;
    }

    public static int queryType4(ArrayList<Player> playerList, String name, StringBuffer totalSalary){
        FileOperations.loadPlayersDataFromFile(playerList);
        long sum = 0;
        if(name == null) return -1;
        if(name.equals("0")) return 0;
        for(int i = 0;i < playerList.size();i++){
            if(playerList.get(i).getClub().matches("(?i).*"+name+".*")){
                sum += playerList.get(i).getWeeklySalary();
            }
        }
        if(sum == 0) return -1;
        Long temp = Long.valueOf(sum);
        totalSalary.append(temp.toString());
        return 1;
    }

}
