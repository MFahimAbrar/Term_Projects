package org.example.term_porject_12_part_two.Interaction;

import org.example.term_porject_12_part_two.Data.Player;

import java.util.Scanner;

public class AddPlayerInteraction {

    public static Player inputPage(){
        String name, country, club, position = null;
        Long weeklySalary = 0L, age = 0L;
        Long number = -1L;
        double height = 0.0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter 0 to exit ");
        System.out.println("Please enter player name: ");
        while(true){
            try{
                name = sc.nextLine().trim();
            }
            catch(NullPointerException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }
        System.out.println("Please enter player country: ");
        while(true){
            try{
                country = sc.nextLine().trim();
            }
            catch(NullPointerException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }
        System.out.println("Please enter player club: ");
        while(true){
            try{
                club= sc.nextLine().trim();
            }
            catch(NullPointerException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }
        System.out.println("Please enter player position: ");
        while(true){
            try{
                position = sc.nextLine().trim();
            }
            catch(NullPointerException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }
        System.out.println("Please enter player age: ");
        while(true){
            try{
                age = Long.parseLong(sc.nextLine().trim(), 10);
            }
            catch(NullPointerException | NumberFormatException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }
        System.out.println("Please enter player jersey number (enter 0 to skip): ");
        while(true){
            try{
                number = Long.parseLong(sc.nextLine().trim(), 10);
            }
            catch(NullPointerException | NumberFormatException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }
        System.out.println("Please enter player weekly salary: ");
        while(true){
            try{
                weeklySalary = Long.parseLong(sc.nextLine().trim(), 10);
            }
            catch(NullPointerException | NumberFormatException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }
        System.out.println("Please enter player height: ");
        while(true){
            try{
                height = Double.parseDouble(sc.nextLine());
            }
            catch(NullPointerException | NumberFormatException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }

        Player newPlayer = new Player(name, country, club, position, age, number, weeklySalary, height);
        return newPlayer;
    }

}
