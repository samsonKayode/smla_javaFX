/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning.controllers;

/**
 *
 * @author SAMSON KAYODE
 */


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    
private static ServerSocket serverSocket;
private static Socket clientSocket;
private static InputStream inputStream;
private static FileOutputStream fileOutputStream;
private static BufferedOutputStream bufferedOutputStream;
private static int filesize = 10000000;
private static int bytesRead;
private static int current = 0;

public static void main(String[] args) throws IOException {

    serverSocket = new ServerSocket(10898);

    System.out.println("Server started. Listening to the port 10898");

    while(true){
        
    
    clientSocket = serverSocket.accept();

    byte[] mybytearray = new byte[filesize];   

    inputStream = clientSocket.getInputStream();
    fileOutputStream = new FileOutputStream("C:\\SMLA_SHARE_DATA\\DATASENT.txt");
    bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

    System.out.println("Receiving...");

    bytesRead = inputStream.read(mybytearray, 0, mybytearray.length);
    current = bytesRead;

    do {
        bytesRead = inputStream.read(mybytearray, current, (mybytearray.length - current));
        if (bytesRead >= 0) {
            current += bytesRead;
        } 
    } while (bytesRead > -1);

    System.out.println("Sever recieved the file");
    bufferedOutputStream.write(mybytearray, 0, current);
    bufferedOutputStream.flush();
    bufferedOutputStream.close();
    inputStream.close();
    
    }
    
    /*
    bufferedOutputStream.write(mybytearray, 0, current);
    bufferedOutputStream.flush();
    bufferedOutputStream.close();
    inputStream.close();
    clientSocket.close();
    serverSocket.close();
            */

    

}
    
}
