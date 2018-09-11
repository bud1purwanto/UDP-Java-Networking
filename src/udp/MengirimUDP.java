/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 *
 * @author Budi Purwanto
 */
public class MengirimUDP {
    public static void main(String[] args) {
        String s, pesan;
        try{
            int port =4400;
            Scanner in;
            in = new Scanner(System.in);
            do {
                System.out.println("Masukkan Pesan ");
                pesan = in.next();
                InetAddress ia = InetAddress.getByName("localhost");
                s = pesan;
                byte[] b = s.getBytes();
                DatagramPacket dp = new DatagramPacket(b, b.length, ia, port);
                DatagramSocket sender = new DatagramSocket();
                port=port+1;
                sender.send(dp);
            } while (s.equalsIgnoreCase("stop")==false);
            
            
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
