package org.example.term_porject_12_part_two.Utility;

import org.example.term_porject_12_part_two.Data.Player;
import org.example.term_porject_12_part_two.Data.PlayerForSale;

import java.io.File;
import java.util.ArrayList;

public class RemovePlayer {

    public static void removePlayerFromMarket(String str){
        ArrayList<PlayerForSale> playerlist = new ArrayList<>();
        FileOperations.loadPlayersForSaleDataFromFile(playerlist);
        for(int i = 0;i < playerlist.size();i++){
            if(playerlist.get(i).toString().contains(str)){
                System.out.println(playerlist.get(i).toString());
                playerlist.remove(i); break;
            }
        }
        FileOperations.storePlayersForSaleDataToFile(playerlist);
    }

    public static void removePlayer(String str){
        ArrayList<Player> playerlist = new ArrayList<>();
        FileOperations.loadPlayersDataFromFile(playerlist);
        for(int i = 0;i < playerlist.size();i++){
            if(playerlist.get(i).toString().contains(str)){
                playerlist.remove(i); break;
            }
        }
        FileOperations.storePlayersDataToFile(playerlist);
    }

}
