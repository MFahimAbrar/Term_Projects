package org.example.term_porject_12_part_two.Data;

import java.io.Serializable;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.StringReader;

public class Player implements Serializable {

     String name;
     String country;
     String club;
     String position;
     long age;
     long number;
     long weeklySalary;

     double height;

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getClub() {
        return club;
    }

    public String getPosition() {
        return position;
    }

    public long getAge() {
        return age;
    }

    public long getNumber() {
        return number;
    }

    public long getWeeklySalary() {
        return weeklySalary;
    }

    public double getHeight() {
        return height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public void setWeeklySalary(long weeklySalary) {
        this.weeklySalary = weeklySalary;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Player(String name, String country, String club, String position, long age, long number, long weeklySalary, double height) {
        this.name = name;
        this.country = country;
        this.club = club;
        this.position = position;
        this.age = age;
        if(number == 0) this.number = 0;
        else this.number = number;
        this.weeklySalary = weeklySalary;
        this.height = height;
    }

    public Player(String lineFromFile){
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
        sc.close();
        System.out.println(this.toString());
    }

    public Player(Player player){
        this.name = player.name;
        this.country = player.country;
        this.club = player.club;
        this.position = player.position;
        this.age = player.age;
        this.number = player.number;
        this.weeklySalary = player.weeklySalary;
        this.height = player.height;
    }

    public Player() {}

    public void displayPlayer(){
        System.out.println("PLayer Name: "+name);
        System.out.println("PLayer Country: "+country);
        System.out.println("PLayer Age: "+age);
        System.out.println("PLayer Height: "+height);
        System.out.println("PLayer Club: "+club);
        System.out.println("PLayer Position: "+position);
        if(number != 0) System.out.println("PLayer Number: "+number);
        else System.out.println("PLayer Number: Not Available");
        System.out.println("PLayer Weekly Salary: "+weeklySalary);
        System.out.println();
    }

    @Override
    public String toString() {
        if(number != 0){
            String lineAtFile = name+","+country+","+age+","+height+","+club+","+position+","+number+","+weeklySalary;
            return lineAtFile;
        }
        else{
            String lineAtFile = name+","+country+","+age+","+height+","+club+","+position+",,"+weeklySalary;
            return lineAtFile;
        }
    }
}

//bw.
