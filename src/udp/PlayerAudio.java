package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author budi
 */
public class PlayerAudio extends Thread{
    public SourceDataLine audioOut = null;
    public DatagramSocket socketIn;
    byte buffer[] = new byte[1024];

    @Override
    public void run() {
        int i = 0;
        DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
        while (ReceivePicture.calling) {            
            try {
                socketIn.receive(incoming);
                buffer = incoming.getData();
                audioOut.write(buffer, 0, buffer.length);
                System.out.println("receive audio "+i++); 
            } catch (IOException ex) {
                Logger.getLogger(PlayerAudio.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
        audioOut.close();
        audioOut.drain();
        System.out.println("Thread stop");
    }
}
