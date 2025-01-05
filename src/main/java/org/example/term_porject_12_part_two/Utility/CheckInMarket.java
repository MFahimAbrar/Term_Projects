package org.example.term_porject_12_part_two.Utility;

import org.example.term_porject_12_part_two.Data.PlayerForSale;

import java.util.ArrayList;

public class CheckInMarket {

    public static boolean checkInMarket(String str){
        ArrayList<PlayerForSale> playerlist = new ArrayList<>();
        FileOperations.loadPlayersForSaleDataFromFile(playerlist);
        for(int i = 0;i < playerlist.size();i++){
            if(playerlist.get(i).toString().contains(str)) return true;
        }
        return false;
    }

}
