package org.example.term_porject_12_part_two.Network_Util;

import org.example.term_porject_12_part_two.Data.Player;

import java.io.Serializable;

public class MessageType2 implements Serializable {

    String message;
    Player player;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}
