/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author Budi Purwanto
 */
public class MenerimaUDP {
    public static void main(String[] args) {
        String s;
        try {
            int port =4400;
            do {
                byte[] buffer = new byte[656];
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                DatagramSocket ds = new DatagramSocket(port);
                port=port+1;
                ds.receive(incoming);
                byte[] data = incoming.getData();
                s = new String(data, 0, data.length);
                System.out.println("Port " + incoming.getPort() + " on " + incoming.
                getAddress() + " sent this message:");
                s=s.trim();
                System.out.println(s);
            } while (s.equalsIgnoreCase("stop")==false);           
        }catch (IOException e) {
            System.err.println(e);
        }
    }
}
