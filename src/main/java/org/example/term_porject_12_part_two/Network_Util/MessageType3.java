package org.example.term_porject_12_part_two.Network_Util;

import org.example.term_porject_12_part_two.Data.PlayerForSale;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageType3 implements Serializable {

    String messgage;

    ArrayList<PlayerForSale> playerForSalelist;

    public String getMessgage() {
        return messgage;
    }

    public void setMessgage(String messgage) {
        this.messgage = messgage;
    }

    public ArrayList<PlayerForSale> getPlayerlist() {
        return playerForSalelist;
    }

    public void setPlayerlist(ArrayList<PlayerForSale> playerForSalelist) {
        this.playerForSalelist = playerForSalelist;
    }
}
