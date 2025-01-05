package org.example.term_porject_12_part_two.Network_Util;

import org.example.term_porject_12_part_two.Data.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageType1 implements Serializable {

    String messgage;

    ArrayList<Player> playerlist;

    public String getMessgage() {
        return messgage;
    }

    public void setMessgage(String messgage) {
        this.messgage = messgage;
    }

    public ArrayList<Player> getPlayerlist() {
        return playerlist;
    }

    public void setPlayerlist(ArrayList<Player> playerlist) {
        this.playerlist = playerlist;
    }

    public MessageType1(String messgage, ArrayList<Player> playerlist){
        this.messgage = messgage;
        this.playerlist = playerlist;
    }

    @Override
    public String toString() {return messgage;}
}
