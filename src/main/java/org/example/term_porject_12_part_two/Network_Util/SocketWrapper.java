package org.example.term_porject_12_part_two.Network_Util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketWrapper {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public SocketWrapper(String s, int port) throws IOException { // used by the client
        this.socket = new Socket(s, port);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public SocketWrapper(Socket s) throws IOException { // used by the server
        this.socket = s;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public Object read() throws IOException, ClassNotFoundException {

        return ois.readObject();
    }

    public void write(Object o) throws IOException {
        try{oos.writeObject(o);} catch(IOException e){
            System.err.println("erererer"); e.printStackTrace();
        }
        try{oos.reset();} catch(IOException e) {
            System.err.println("Errr"); e.printStackTrace();
        }
        oos.reset();
        oos.flush();
    }

    public void closeConnection() throws IOException {
        System.out.println("In closing method");
        ois.close();
        oos.close();
        socket.close();
    }

    public boolean isClosed(){
        return socket.isClosed();
    }

    public boolean isConnected(){
        return socket.isConnected();
    }

    public void reconnect() {
        while (true) {
            try {
                socket = new Socket("127.0.0.1", 44444);
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                System.out.println("Reconnected successfully.");
                break;
            } catch (IOException e) {
                System.err.println("Reconnection failed. Retrying...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
