package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author budi
 */
public class PerekamAudio extends Thread{
    public TargetDataLine audioIn = null;
    public DatagramSocket socketOut;
    byte byte_buff[] = new byte[1024];
    public InetAddress IPServer;
    public int PortServer;

    @Override
    public void run() {
        int i = 0;
        while (TakePicture.calling) {
            try {
                audioIn.read(byte_buff, 0, byte_buff.length);
                DatagramPacket data = new DatagramPacket(byte_buff, byte_buff.length, IPServer, PortServer);
                System.out.println("send audio "+i++);
                
                socketOut.send(data);
            } catch (IOException ex) {
                Logger.getLogger(PerekamAudio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        audioIn.close();
        audioIn.drain();
        System.out.println("Thread stop");
    }
}
