package org.example.term_porject_12_part_two.Utility;

import org.example.term_porject_12_part_two.Data.Player;
import org.example.term_porject_12_part_two.Data.PlayerForSale;

import java.util.ArrayList;

public class AddPlayer {

    public static int addPlayer(ArrayList<Player> playerList, Player newPlayer){

        FileOperations.loadPlayersDataFromFile(playerList);
        if(newPlayer.getName().equals("0")) return 0;
        if(newPlayer.getName().isEmpty()) return -1;
        if(newPlayer.getCountry().isEmpty()) return -1;
        if(newPlayer.getClub().isEmpty()) return -1;
        if(newPlayer.getPosition().isEmpty()) return -1;
        if(newPlayer.getAge() <= 0L) return -1;
        if(newPlayer.getNumber() < 0L) return -1;
        if(newPlayer.getHeight() <= 0.0) return -1;
        int i;
        for(i = 0;i < playerList.size();i++){
            if(playerList.get(i).getName().equalsIgnoreCase(newPlayer.getName())) break;
        }
        if(i < playerList.size()) {return -2;}
        playerList.add(newPlayer);
        FileOperations.storePlayersDataToFile(playerList);
        return 1;
    }

    public static void addPlayerToMarket(ArrayList<PlayerForSale> playerlist, PlayerForSale newPlayer){
        FileOperations.loadPlayersForSaleDataFromFile(playerlist);
        playerlist.add(newPlayer);
        FileOperations.storePlayersForSaleDataToFile(playerlist);
    }

}
