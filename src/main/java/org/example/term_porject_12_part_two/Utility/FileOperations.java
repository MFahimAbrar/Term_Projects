package org.example.term_porject_12_part_two.Utility;

import org.example.term_porject_12_part_two.Data.Player;
import org.example.term_porject_12_part_two.Data.PlayerForSale;
import org.example.term_porject_12_part_two.Network_Util.MessageType1;
import org.example.term_porject_12_part_two.Network_Util.MessageType2;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FileOperations {

    private static final String FILE_NAME = "players.txt";
    private static final String FILE_NAME2 = "credentiallist.txt";
    private static final String FILE_NAME3 = "playersforsalelist.txt";
    protected synchronized static void loadPlayersDataFromFile(ArrayList<Player> playersList){

        try (FileReader fr = new FileReader(FILE_NAME);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            try{
                while(true){
                    line = br.readLine();
                    if (line == null) break;
                    playersList.add(new Player(line));
                }
            }
            catch(IOException e){
                System.err.println("Exception: "+e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    protected synchronized static void storePlayersDataToFile(ArrayList<Player> playersList){
        try (FileWriter fw = new FileWriter(FILE_NAME);
             BufferedWriter bw = new BufferedWriter(fw)) {

            try{
                for (Player player : playersList) {
//                System.out.println(player.toString()); // Optional debug statement
                    bw.write(player.toString());
                    bw.write(System.lineSeparator());
                }
            }
            catch(IOException e){
                System.err.println("Exception: "+e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    protected synchronized static void loadTeamCredentials(HashMap<String, String> credentials){

        try (FileReader fr = new FileReader(FILE_NAME2);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            try{
                while(true){
                    line = br.readLine();
                    if (line == null) break;
                    String[] arg = line.split("=");
                    credentials.put(arg[0], arg[1]);
                }
            }
            catch(IOException e){
                System.err.println("Exception: "+e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    protected synchronized static void storeTeamCredentials(HashMap<String, String> credentials){
        try (FileWriter fw = new FileWriter(FILE_NAME2);
             BufferedWriter bw = new BufferedWriter(fw)) {

            try{
                for(String key: credentials.keySet()){
                    bw.write(key+"="+credentials.get(key));
                    bw.write(System.lineSeparator());
                }
            }
            catch(IOException e){
                System.err.println("Exception: "+e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    protected synchronized static void loadPlayersForSaleDataFromFile(ArrayList<PlayerForSale> playersList){

        try (FileReader fr = new FileReader(FILE_NAME3);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            try{
                while(true){
                    line = br.readLine();
                    if (line == null) break;
                    playersList.add(new PlayerForSale(line));
                }
            }
            catch(IOException e){
                System.err.println("Exception: "+e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    protected synchronized static void storePlayersForSaleDataToFile(ArrayList<PlayerForSale> playersList){
        try (FileWriter fw = new FileWriter(FILE_NAME3);
             BufferedWriter bw = new BufferedWriter(fw)) {

            try{
                for (Player player : playersList) {
//                System.out.println(player.toString()); // Optional debug statement
                    bw.write(player.toString());
                    bw.write(System.lineSeparator());
                }
            }
            catch(IOException e){
                System.err.println("Exception: "+e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
