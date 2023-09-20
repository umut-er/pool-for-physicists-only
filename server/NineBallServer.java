package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import gameobjects.Ball;

public class NineBallServer { // TODO: Maybe do a PoolServer parent class?
    public static final int INITIALIZATION_INFO = 110; // tells the client to process an initialization
    public static final int TURN_INFO = 111; // tells the client to process a turn 
    public static final int HIT_INFO = 112;
    public static final int FOUL_INFO = 113; // tells the client to process a foul.

    private ServerSocket ss;
    public static final int MAX_PLAYER_NUM = 2;
    private int numOfPlayers;
    private ServerSideConnection[] serverSideConnections;
    private String[] usernames;
    private boolean turn;

    public NineBallServer(){
        System.out.println("---- Nine Ball Server ----");
        numOfPlayers = 0;
        serverSideConnections = new ServerSideConnection[MAX_PLAYER_NUM];
        usernames = new String[MAX_PLAYER_NUM];

        double rand = Math.random();
        turn = (rand >= 0.5);

        try {
            ss = new ServerSocket(4999);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void acceptConnections(){
        System.out.println("Waiting for players...");
        try {
            while(numOfPlayers < 2){
                Socket s = ss.accept();
                numOfPlayers++;
                serverSideConnections[numOfPlayers-1] = new ServerSideConnection(s, numOfPlayers);
                Thread t = new Thread(serverSideConnections[numOfPlayers-1]);
                t.start();
            }
            System.out.println("Connections established, game starting!");
            ArrayList<Ball> rack = Ball.getStandardNineBallArray();
            while(usernames[1] == null){} // wait for usernames to be processed by the server.
            for(ServerSideConnection ssc : serverSideConnections)
                ssc.sendInitializationInformation(rack);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ServerSideConnection implements Runnable{
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        private int playerID;

        public ServerSideConnection(Socket s, int id){
            socket = s;
            playerID = id;
            try {
                dataIn = new DataInputStream(s.getInputStream());
                dataOut = new DataOutputStream(s.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendInitializationInformation(ArrayList<Ball> rack){
            try {
                // Send the usernames.
                dataOut.writeInt(INITIALIZATION_INFO);
                for(int i = 0; i < MAX_PLAYER_NUM; i++)
                    dataOut.writeUTF(usernames[i]);
                
                // Send the rack.
                ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectOut.writeObject(rack);

                // flush
                dataOut.flush();
                objectOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            sendTurnInformation();
        }

        public void sendTurnInformation(){
            try {
                dataOut.writeInt(TURN_INFO);
                dataOut.writeBoolean(turn);
                dataOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        public void sendHitInformation(HitInfo hit){
            try {
                dataOut.writeInt(HIT_INFO);

                ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectOut.writeObject(hit);

                dataOut.flush();
                objectOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        public void sendFoulInformation(FoulInfo foul){
            try {
                dataOut.writeInt(FOUL_INFO);

                ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectOut.writeObject(foul);

                dataOut.flush();
                objectOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        public void run(){
            try {
                dataOut.writeInt(playerID);
                dataOut.flush();
                String username = dataIn.readUTF();
                usernames[playerID-1] = username;
                System.out.println("Player #" + playerID + " connected with username: " + username);
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(true){
                try {
                    int request = dataIn.readInt();
                    if(request == PoolClient.HIT_START_INFO){
                        // Receive the info
                        ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
                        HitInfo currentHit = (HitInfo)objectIn.readObject();

                        // Send the info back (an interesting way to do it for sure)
                        for(int i = 0; i < MAX_PLAYER_NUM; i++){
                            serverSideConnections[i].sendHitInformation(currentHit);
                        }
                    }
                    else if(request == PoolClient.HIT_END_INFO){
                        // Receive the info
                        ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
                        HitEndInfo currentHit = (HitEndInfo)objectIn.readObject();

                        // Generate FoulInfo and turn TODO: Generate WinInfo and send before turn info.
                        FoulInfo foul = new FoulInfo();
                        turn = !turn;
                        System.out.println("GENERATED!!!");
                        
                        // Send turn information
                        for(int i = 0; i < MAX_PLAYER_NUM; i++){
                            serverSideConnections[i].sendTurnInformation();
                        }

                        // Send FoulInfo
                        for(int i = 0; i < MAX_PLAYER_NUM; i++){
                            serverSideConnections[i].sendFoulInformation(foul);
                        }

                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }

            }
        }
    }

    public static void main(String[] args) {
        NineBallServer server = new NineBallServer();
        server.acceptConnections();
    }
}
