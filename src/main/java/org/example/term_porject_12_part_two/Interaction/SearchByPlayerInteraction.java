package org.example.term_porject_12_part_two.Interaction;

import java.util.Scanner;

public class SearchByPlayerInteraction {


    public static int mainPage(){
        System.out.println("Player Searching Options:\n" +
                "(1) By Player Name\n" +
                "(2) By Club and Country\n" +
                "(3) By Position\n" +
                "(4) By Salary Range\n" +
                "(5) Country-wise player count\n" +
                "(6) Back to Main Menu");

        int i;
        Scanner sc = new Scanner(System.in);
        while(true){
            try{
                i = Integer.parseInt(sc.nextLine(), 10);
            }
            catch( NumberFormatException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }
        return i;
    }

    public static void queryType1(StringBuffer name){
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

    public static void queryType2(StringBuffer country, StringBuffer club){
        System.out.println("Enter 0 to exit ");
        System.out.println("Please enter a country: ");
        Scanner sc = new Scanner(System.in);
        while(true){
            try{
                country.append(sc.nextLine());
            }
            catch(NullPointerException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }
        System.out.println("Please enter a club: ");
        while(true){
            try{
                club.append(sc.nextLine());
            }
            catch(NullPointerException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }

    }

    public static void queryType3(StringBuffer position){
        System.out.println("Enter 0 to exit ");
        System.out.println("Please enter a position: ");
        Scanner sc = new Scanner(System.in);
        while(true){
            try{
                position.append(sc.nextLine());
            }
            catch(NullPointerException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }
    }
    public static void queryType4(StringBuffer lowerSalary, StringBuffer upperSalary){
        System.out.println("Enter 0 to exit ");
        long temp;
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a lower bound of salary: ");
        while(true){
            try{
                lowerSalary.append(sc.nextLine());
            }
            catch(NullPointerException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }
        System.out.println("Please enter a upper bound of salary: ");
        while(true){
            try{
                upperSalary.append(sc.nextLine());
            }
            catch(NullPointerException e){
                ErrorMessage.invalidCommand();
                continue;
            }
            break;
        }

    }

}
