package org.example.term_porject_12_part_two.Network_Util;

import org.example.term_porject_12_part_two.Data.PlayerForSale;

import java.io.Serializable;

public class MessageType4 implements Serializable {

    String message;
    PlayerForSale playerForSale;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PlayerForSale getPlayer() {
        return playerForSale;
    }

    public void setPlayer(PlayerForSale playerForSale) {
        this.playerForSale = playerForSale;
    }


}
