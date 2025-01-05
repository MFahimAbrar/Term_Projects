package org.example.term_porject_12_part_two.Interaction;

import org.example.term_porject_12_part_two.Data.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class SearchByClubInteraction {

    public static int mainPage(){

        System.out.println("Club Searching Options:\n" +
                "(1) Player(s) with the maximum salary of a club\n" +
                "(2) Player(s) with the maximum age of a club\n" +
                "(3) Player(s) with the maximum height of a club\n" +
                "(4) Total yearly salary of a club\n" +
                "(5) Back to Main Menu");
        int i;
        Scanner sc = new Scanner(System.in);
        i = sc.nextInt();
        return i;
    }

    public static void queryType1(ArrayList<Player> playerList, StringBuffer name){
        System.out.println("Enter 0 to exit ");
        System.out.println("Please enter a name: ");
        Scanner sc = new Scanner(System.in);
        while(true){
            try{
                name.append(sc.nextLine());
            }
            catch(NullPointerException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }
    }

    public static void queryType2(ArrayList<Player> playerList, StringBuffer name){
        System.out.println("Enter 0 to exit ");
        System.out.println("Please enter a name: ");
        Scanner sc = new Scanner(System.in);
        while(true){
            try{
                name.append(sc.nextLine());
            }
            catch(NullPointerException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }
    }

    public static void queryType3(ArrayList<Player> playerList, StringBuffer name){
        System.out.println("Enter 0 to exit ");
        System.out.println("Please enter a name: ");
        Scanner sc = new Scanner(System.in);
        while(true){
            try{
                name.append(sc.nextLine());
            }
            catch(NullPointerException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }
    }

    public static void queryType4(ArrayList<Player> playerList, StringBuffer name){
        System.out.println("Enter 0 to exit ");
        System.out.println("Please enter a name: ");
        Scanner sc = new Scanner(System.in);
        while(true){
            try{
                name.append(sc.nextLine());
            }
            catch(NullPointerException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }
    }


}
