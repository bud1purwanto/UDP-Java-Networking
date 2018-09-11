/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;

import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 *
 * @author budi
 */
public class ReceivePicture extends javax.swing.JFrame {
    int portReceive = 4420;
    int portSend = 5100;
    int portAudio = 2000;
    String ipPublic = "localhost";
    
    public static boolean calling = false;
    
    public static AudioFormat getaudioformat(){
        float sampleRate = 8000.0F;
        int sampleSizeinBite = 16;
        int channel = 2;
        boolean signed = true;
        boolean bigEndien = false;
        return new AudioFormat(sampleRate, sampleSizeinBite, channel, signed, bigEndien);
    }
    public SourceDataLine data_in, audio_out;
    
    public ReceivePicture() {
        initComponents();
        initAudio();
        initPicture();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Gambar = new javax.swing.JLabel();
        IP = new javax.swing.JLabel();
        Port = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Gambar)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(IP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 628, Short.MAX_VALUE)
                .addComponent(Port)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Gambar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 321, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IP)
                    .addComponent(Port))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public void initAudio(){
        try {
            AudioFormat format = getaudioformat();
            DataLine.Info info_cut = new DataLine.Info(SourceDataLine.class, format);
            if (!AudioSystem.isLineSupported(info_cut)) {
                System.out.println("not supported");
            }
            audio_out = (SourceDataLine) AudioSystem.getLine(info_cut);
            audio_out.open(format);
            audio_out.start();
            
            PlayerAudio p =  new PlayerAudio();
            p.socketIn= new DatagramSocket(portAudio);
            p.audioOut = audio_out;
            ReceivePicture.calling = true;
            p.start();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(ReceivePicture.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(ReceivePicture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initPicture(){
        try {
            Controller data =  new Controller();
            data.start();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public class Controller extends Thread{
        @Override
        public void run() {
            while (TakePicture.sendPic = true) {            
                receiveImage();
                captureCamera();
                sendCaptureImage();
                DeleteFile();
            }
        }
    }
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        setTitle("Client");
    }//GEN-LAST:event_formWindowOpened
    
    private void receiveImage(){
        try {
            DatagramSocket clientsocket=new DatagramSocket(portReceive);
            byte[] receivedata = new byte[92024];
            DatagramPacket recv_packet = new DatagramPacket(receivedata, receivedata.length);
            clientsocket.receive(recv_packet);       
            byte[] buff = recv_packet.getData();
                
            String pesan = new String(buff, 0, buff.length);
            pesan=pesan.trim();
//            System.out.println("pesan = " + pesan);
            
            if (pesan.equalsIgnoreCase("stop")) {
                clientsocket.close();
                System.exit(0);
            } else {
                InputStream in = new ByteArrayInputStream(buff);
                BufferedImage img = ImageIO.read(in);
                ImageIO.write(img, "jpg", new File("/home/budi/kampret.jpg"));
                ImageIcon set = new ImageIcon(img);
                Gambar.setIcon(set);
                clientsocket.close();

                InetAddress ipaddress = recv_packet.getAddress();
                int Ports = recv_packet.getPort();
                IP.setText(ipaddress.toString());
                Port.setText(Integer.toString(Ports));
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    public void captureCamera(){
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        try {
            grabber.start();
            opencv_core.IplImage img = grabber.grab();
            if (img!=null) {
                cvSaveImage("capture-client.jpg", img);
            }
            grabber.release();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendCaptureImage(){
        try{
            BufferedImage img = ImageIO.read(new File("/media/budi/Dell_Data1/Documents/PemrogramanJaringan/UDP/capture-client.jpg"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();        
            ImageIO.write(img, "jpg", baos);
            baos.flush();
            byte[] buffer = baos.toByteArray();

            DatagramSocket serverSocket = new DatagramSocket();       
            InetAddress IPAddress = InetAddress.getByName(ipPublic);
            System.out.println(buffer.length);

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, IPAddress, portSend);

            serverSocket.send(packet);
            serverSocket.close();

        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void DeleteFile(){
        File file = new File("/media/budi/Dell_Data1/Documents/PemrogramanJaringan/UDP/capture-client.jpg");
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] fd = file.listFiles();
                for(File f : fd){
                    f.delete();
                }
            }
            else{
                file.delete();
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReceivePicture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReceivePicture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReceivePicture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReceivePicture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReceivePicture().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Gambar;
    private javax.swing.JLabel IP;
    private javax.swing.JLabel Port;
    // End of variables declaration//GEN-END:variables
}
