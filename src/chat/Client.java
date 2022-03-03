import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Client{
    static int port = 6666;
    public static void main (String [] args) throws UnknownHostException, IOException{
        Scanner reader = new Scanner(System.in);
        InetAddress ip = InetAddress.getByName("localhost");
        //Stablish the connection
        Socket socket = new Socket(ip, port);
        //Obtain inputs and outputs streams
        DataInputStream dataIS = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOS = new DataOutputStream(socket.getOutputStream());
        //send message (thread)
        Thread sendMessage = new Thread(new Runnable(){
            @Override
            public void run(){
                while(true){
                    //read the message to deliver
                    String message = reader.nextLine();
                    try{
                        dataOS.writeUTF(message);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        //read message thread
        Thread readMessage = new Thread(new Runnable(){
            @Override
            public void run(){
                while(true){
                    try{
                        //read th message sent to this client
                        String message = dataIS.readUTF();
                        System.out.println(message);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        sendMessage.start();
        readMessage.start();
    }
}