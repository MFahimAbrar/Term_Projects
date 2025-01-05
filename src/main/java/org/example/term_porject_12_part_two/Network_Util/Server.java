package org.example.term_porject_12_part_two.Network_Util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javafx.application.Platform;
import org.example.term_porject_12_part_two.Utility.FileOperations;

public class Server extends FileOperations{


    HashMap clientMap, credentials;
    Server(){
        System.out.println("Process ID: " + ProcessHandle.current().pid());
        clientMap = new HashMap<String, SocketWrapper>();
        credentials = new HashMap<String, String>();
        FileOperations.loadTeamCredentials(credentials);
        for(Object s: credentials.keySet()) System.out.println((String) s);
//        try{
//            ServerSocket serverSocket = new ServerSocket(44444);
//            while (true) {
//                try (Socket clientSocket = serverSocket.accept()) {
//                    serve(clientSocket);
//                } catch (ClassNotFoundException e) {
//                    System.out.println("Error while serving client: " + e);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                System.out.println("accept iteration complete");
//            }
//        } catch (IOException e) {
//            System.out.println("Server failed to start: " + e);
//        }
        try{
            ServerSocket serverSocket = new ServerSocket(44444);
            while(true){
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        }
        catch (IOException | ClassNotFoundException | InterruptedException e){
            System.err.println(e);
            e.printStackTrace();
        }

    }

    public void serve(Socket socket) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("Inside Serve method" + socket.isClosed());
        new ReadWriteThreadServer(socket, this);
    }

    public static void main(String[] args) {
        new Server();
        System.out.println("Server Closing");
    }

}
