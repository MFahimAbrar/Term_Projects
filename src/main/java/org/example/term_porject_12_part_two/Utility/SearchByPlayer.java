package org.example.term_porject_12_part_two.Utility;

import org.example.term_porject_12_part_two.Data.Player;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchByPlayer {


    public static int queryType1(ArrayList<Player> playerList, String name, ArrayList<Player> queryResult) throws IOException, ClassNotFoundException {
        FileOperations.loadPlayersDataFromFile(playerList);
        if(name.equals("0")) return 0;
        queryResult.clear();
        for(int i = 0;i < playerList.size();i++){
            if(playerList.get(i).getName().matches("(?i).*"+name+".*")){
                queryResult.add(playerList.get(i));
            }
        }
        return 1;
    }

    public static int queryType2(ArrayList<Player> playerList, String country, String club, ArrayList<Player> queryResult){
        FileOperations.loadPlayersDataFromFile(playerList);
        if(country.equals("0") && club.equals("0")) return 0;
        queryResult.clear();
        for(int i = 0;i < playerList.size();i++){
            if((playerList.get(i).getCountry().matches("(?i).*"+country+".*") || country.equalsIgnoreCase("ANY")) && (club.equalsIgnoreCase("ANY") || playerList.get(i).getClub().matches("(?i).*"+club+".*"))){
                queryResult.add(playerList.get(i));
            }
        }
        return 1;
    }
    public static int queryType3(ArrayList<Player> playerList, String position, ArrayList<Player> queryResult){
        FileOperations.loadPlayersDataFromFile(playerList);
        if(position.equals("0")) return 0;
        if(!position.equalsIgnoreCase("Batsman") &&
                !position.equalsIgnoreCase("Bowler") &&
                !position.equalsIgnoreCase("Allrounder") &&
                !position.equalsIgnoreCase("Wicketkeeper")) return -1;
        queryResult.clear();
        for(int i = 0;i < playerList.size();i++){
            if(playerList.get(i).getPosition().matches("(?i).*"+position+".*")){
                queryResult.add(playerList.get(i));
            }
        }
        return 1;
    }

    public static int queryType4(ArrayList<Player> playerList, String lower, String upper, ArrayList<Player> queryResult){
        FileOperations.loadPlayersDataFromFile(playerList);
        long lowerSalary, upperSalary;
        try{
            lowerSalary = Long.parseLong(lower, 10);
        }
        catch (NumberFormatException e){
            return -1;
        }
        try{
            upperSalary = Long.parseLong(upper, 10);
        }
        catch (NumberFormatException e){
            return -1;
        }
        if(lowerSalary == 0 && upperSalary == 0) return 0;
        if(lowerSalary <= 0 || upperSalary <= 0) return -1;
        queryResult.clear();
        for(int i = 0;i < playerList.size();i++){
            if(playerList.get(i).getWeeklySalary() >= lowerSalary &&
                    playerList.get(i).getWeeklySalary() <= upperSalary ){
                queryResult.add(playerList.get(i));
            }
        }
        return 1;
    }

    public static HashMap<String, Long> queryType5(ArrayList<Player> playerList){
        FileOperations.loadPlayersDataFromFile(playerList);
        HashMap<String, Long> countryWiseCount = new HashMap<String, Long>();
        for(int i = 0;i < playerList.size();i++){
            String temp = playerList.get(i).getCountry();
            if(countryWiseCount.containsKey(temp.toLowerCase()) == false){
                countryWiseCount.put(temp.toLowerCase(), 1L);
            }
            else{
                countryWiseCount.put(temp.toLowerCase(), countryWiseCount.get(temp.toLowerCase())+1);
            }
        }
        return countryWiseCount;
    }

}
