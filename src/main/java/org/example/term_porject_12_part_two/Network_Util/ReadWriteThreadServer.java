package org.example.term_porject_12_part_two.Network_Util;

import org.example.term_porject_12_part_two.Data.Player;
import org.example.term_porject_12_part_two.Data.PlayerForSale;
import org.example.term_porject_12_part_two.Utility.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.lang.reflect.Constructor;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadWriteThreadServer extends FileOperations implements Runnable{

    private SocketWrapper clientSocket;
    private Server server;

    public ReadWriteThreadServer(Socket socket, Server server) throws IOException, ClassNotFoundException {
        clientSocket = new SocketWrapper(socket);
        this.server = server;
        Thread t = new Thread(this);
        System.out.println(clientSocket + " " + clientSocket.isConnected() + " " + clientSocket.isClosed());
        System.out.println("Constructor Process ID: " + ProcessHandle.current().pid());
        t.start();
    }


    @Override
    public void run(){
//        System.out.println("aa " + clientSocket.isClosed());
//        clientSocket.reconnect(); System.out.println("bb " + clientSocket.isClosed());
        System.out.println("IO thread begin with process ID: " +ProcessHandle.current().pid());
        Object o = null;
        System.out.println("socket status " + clientSocket + clientSocket.isConnected() + clientSocket.isClosed());
        try{
            int flag = 1;
            while(flag == 1){
                System.out.println("socket status " + clientSocket + clientSocket.isConnected() + clientSocket.isClosed());
                String s1 = (String)clientSocket.read();

                if(s1.equals("GUESTUSER")) break;
                else if(s1.matches("(?i).*"+"LOGINLOGIN"+".*")){
                    String[] arg = s1.split("#");
                    System.out.println("Begin check1");
                    if(!server.credentials.isEmpty() && server.credentials.containsKey(arg[1])){
                        clientSocket.write("SUCCESSSUCCESS");
                        while(true){
                            String s2 = (String) clientSocket.read();
                            System.out.println(s2);
                            if(s2.equals("BACKBACK")) break;
                            if (server.credentials.get(arg[1]).equals(s2)) {
                                clientSocket.write("CORRECTCORRECT");
                                System.out.println(clientSocket);
                                flag = 0; break;
                            } else {
                                clientSocket.write("WRONGWRONG");
                                System.out.println("Done1");
                            }
                        }
                    }
                    else clientSocket.write("UNSUCCESSUNSUCCESS");
                }
                else if(s1.matches("(?i).*"+"NEWUSERNEWUSER"+".*")){
                    String[] arg = s1.split("#");
                    System.out.println("Begin check2");
                    if(server.credentials.containsKey(arg[1])){
                        clientSocket.write("ALREADYALREADY");
                    }
                    else{
                        System.out.println("Done2");
                        clientSocket.write("SUCCESSSUCCESS2");
                        String s2 = null;
                        try{s2 = (String)clientSocket.read();}
                        catch (Exception e) {
                            System.out.println(e);
                        }
                        System.out.println("Done3");
                        if(s2.equals("BACKBACK")) continue;
                        server.credentials.put(arg[1], s2);

                        FileOperations.storeTeamCredentials(server.credentials);
                        flag = 0; break;
                    }
                }
            }

            while(true){
                System.out.println("Log00 " + clientSocket + clientSocket.isClosed() + clientSocket.isConnected());
                try {o = clientSocket.read();}
                catch (StreamCorruptedException e) {
                    System.err.println(e); e.printStackTrace();}
                System.out.println("Log0");
                if(o instanceof String){
                    System.out.println((String) o);
                    String[] arg = ((String) o).split("=");
                    if(arg[0].equals("playername")){
                        ArrayList<Player> playerList = new ArrayList<>();
                        ArrayList<Player> queryResult = new ArrayList<>();
                        SearchByPlayer.queryType1(playerList, arg[1], queryResult);
                        clientSocket.write(queryResult);
                    }
                    else if(arg[0].equals("playercountryclub")){
                        ArrayList<Player> playerList = new ArrayList<>();
                        ArrayList<Player> queryResult = new ArrayList<>();
                        SearchByPlayer.queryType2(playerList, arg[1], arg[2], queryResult);
                        clientSocket.write(queryResult);
                    }
                    else if(arg[0].equals("playerposition")){
                        ArrayList<Player> playerList = new ArrayList<>();
                        ArrayList<Player> queryResult = new ArrayList<>();
                        SearchByPlayer.queryType3(playerList, arg[1], queryResult);
                        clientSocket.write(queryResult);
                    }
                    else if(arg[0].equals("playersalary")){
                        ArrayList<Player> playerList = new ArrayList<>();
                        ArrayList<Player> queryResult = new ArrayList<>();
                        SearchByPlayer.queryType4(playerList, arg[1], arg[2], queryResult);
                        clientSocket.write(queryResult);
                    }
                    else if(arg[0].equals("countrywisecount")){
                        ArrayList<Player> playerlist = new ArrayList<>();
                        HashMap<String, Long> countryWiseCount = SearchByPlayer.queryType5(playerlist);
                        clientSocket.write(countryWiseCount);
                    }
                    else if(arg[0].equals("clubsalary")){
                        ArrayList<Player> playerList = new ArrayList<>();
                        ArrayList<Player> queryResult = new ArrayList<>();
                        SearchByClub.queryType1(playerList, arg[1], queryResult);
                        clientSocket.write(queryResult);
                    }
                    else if(arg[0].equals("clubage")){
                        ArrayList<Player> playerList = new ArrayList<>();
                        ArrayList<Player> queryResult = new ArrayList<>();
                        SearchByClub.queryType2(playerList, arg[1], queryResult);
                        clientSocket.write(queryResult);
                    }
                    else if(arg[0].equals("clubheight")){
                        ArrayList<Player> playerList = new ArrayList<>();
                        ArrayList<Player> queryResult = new ArrayList<>();
                        SearchByClub.queryType3(playerList, arg[1], queryResult);
                        clientSocket.write(queryResult);
                    }
                    else if(arg[0].equals("clubtotalsalary")){
                        ArrayList<Player> playerList = new ArrayList<>();
                        StringBuffer stringBuffer = new StringBuffer();
                        SearchByClub.queryType4(playerList, arg[1], stringBuffer);
                        clientSocket.write(stringBuffer.toString());
                    }
                    else if(arg[0].equals("addplayer")){
                        Player newPlayer = (Player) clientSocket.read();
                        ArrayList<Player> playerList = new ArrayList<>();
                        int i = AddPlayer.addPlayer(playerList, newPlayer);
                        clientSocket.write(i);
                    }
                    else if(arg[0].equals("viewteamplayer")){
                        ArrayList<Player> playerlist = new ArrayList<>();
                        ArrayList<Player> queryResult = new ArrayList<>();
                        SearchByPlayer.queryType2(playerlist, "ANY", arg[1], queryResult);
                        clientSocket.write(queryResult);
                        StringBuffer stringBuffer = new StringBuffer();
                        SearchByClub.queryType4(playerlist, arg[1], stringBuffer);
                        clientSocket.write(stringBuffer.toString());
                    }
                    else if(arg[0].equals("viewteamplayerforsell")){
                        ArrayList<Player> playerlist = new ArrayList<>();
                        ArrayList<Player> queryResult = new ArrayList<>();
                        SearchByPlayer.queryType2(playerlist, "ANY", arg[1], queryResult);
                        clientSocket.write(queryResult);
                    }
                    else if(arg[0].equals("changepassword")){
                        String newpass = (String) clientSocket.read();
                        String[] newarg = newpass.split("=");
                        if(newarg.length == 1) continue;
                        server.credentials.remove(newarg[0]);
                        server.credentials.put(newarg[0], newarg[1]);
                        FileOperations.storeTeamCredentials(server.credentials);
                    }
                    else if(arg[0].equals("addtomarket")){
                        PlayerForSale newPlayer = (PlayerForSale) clientSocket.read();
                        ArrayList<PlayerForSale> playerList = new ArrayList<>();
                        AddPlayer.addPlayerToMarket(playerList, newPlayer);
                    }
                    else if(arg[0].equals("checkinmarket")){
                        if(CheckInMarket.checkInMarket(arg[1])) clientSocket.write("found");
                        else clientSocket.write("notfound");
                    }
                    else if(arg[0].equals("removeplayerfrommarket")){
                        RemovePlayer.removePlayerFromMarket(arg[1]);
                    }
                    else if(arg[0].equals("viewallplayerforsell")){
                        ArrayList<PlayerForSale> playerlist = new ArrayList<>();
                        FileOperations.loadPlayersForSaleDataFromFile(playerlist);
                        clientSocket.write(playerlist);
                    }
                    else if(arg[0].equals("buyfrommarket")){
                        if(arg[1].contains(arg[2])) {clientSocket.write("sameteamerror");}
                        else{
                            if(!CheckInMarket.checkInMarket(arg[1])) {clientSocket.write("notfound");}
                            else{
                                Player oldPlayer = new Player(arg[1]);
                                Player newPlayer = new Player(arg[1]);
                                newPlayer.setClub(arg[2]);
                                RemovePlayer.removePlayer(oldPlayer.toString());
                                ArrayList<Player> playerlist = new ArrayList<>();
                                AddPlayer.addPlayer(playerlist, newPlayer);
                                RemovePlayer.removePlayerFromMarket(arg[1]);
                                clientSocket.write("found");
                            }
                        }
                    }
                }
            }

        }
        catch(IOException | ClassNotFoundException e){
            System.err.println(e);
        }
    }

}
