package org.example.term_porject_12_part_two.Data;

import java.io.BufferedReader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Scanner;

public class PlayerForSale extends Player implements Serializable {

    Long sellPrice = 0L;
    public PlayerForSale(Player player, Long sellPrice){
        super(player);
        this.sellPrice = sellPrice;
    }

    public Long getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Long sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Override
    public String toString() {
        if(number != 0){
            String lineAtFile = name+","+country+","+age+","+height+","+club+","+position+","+number+","+weeklySalary+","+sellPrice;
            return lineAtFile;
        }
        else{
            String lineAtFile = name+","+country+","+age+","+height+","+club+","+position+",,"+weeklySalary+","+sellPrice;
            return lineAtFile;
        }
    }

    public PlayerForSale(String lineFromFile){
        System.out.println(lineFromFile);
        BufferedReader br = new BufferedReader(new StringReader(lineFromFile));
        Scanner sc = new Scanner(br);
        sc.useDelimiter(",");
        String token;
        this.name = sc.next();
        this.country = sc.next();
        this.age = Long.parseLong(sc.next());
        this.height = Double.parseDouble(sc.next());
        this.club = sc.next();
        this.position = sc.next();
        token = sc.next();
        if(!token.isEmpty()) this.number = Long.parseLong(token);
        else this.number = 0;
        this.weeklySalary = Long.parseLong(sc.next());
        this.sellPrice = Long.parseLong(sc.next());
        sc.close();
    }
}
