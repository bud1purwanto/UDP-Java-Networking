/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_CAP_PROP_FRAME_HEIGHT;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvCreateCameraCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvQueryFrame;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSetCaptureProperty;
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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import org.opencv.videoio.Videoio;
/**
 *
 * @author Budi Purwanto
 */
public class TakePicture extends javax.swing.JFrame {
    int portSend = 4420;
    int portReceive = 5100;
    int portAudio = 2000;
    String ipPublic = "192.168.1.23";
    
    public static boolean calling = false;
    public static boolean sendPic = false;
    
    public static AudioFormat getaudioformat(){
        float sampleRate = 8000.0F;
        int sampleSizeinBite = 16;
        int channel = 2;
        boolean signed = true;
        boolean bigEndien = false;
        return new AudioFormat(sampleRate, sampleSizeinBite, channel, signed, bigEndien);
    }
    TargetDataLine audio_in;
    
    public TakePicture() {
        initComponents();
        initAudio();
        initPicture();
    }
    
    public void initAudio(){
        try {
            AudioFormat format = getaudioformat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("not supported");
            }
            audio_in = (TargetDataLine) AudioSystem.getLine(info);
            audio_in.open(format);
            audio_in.start();
            
            PerekamAudio data =  new PerekamAudio();
            InetAddress inet = InetAddress.getByName(ipPublic);
            data.audioIn = audio_in;
            data.socketOut= new DatagramSocket();
            data.IPServer = inet;
            data.PortServer = portAudio;
            TakePicture.calling = true;
            data.start();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(TakePicture.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(TakePicture.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(TakePicture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initPicture(){
        try {
            Controller data =  new Controller();
            sendPic = true;
            data.start();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OpenWebcam = new javax.swing.JButton();
        Exit = new javax.swing.JButton();
        Start = new javax.swing.JButton();
        Stop = new javax.swing.JButton();
        Status = new javax.swing.JLabel();
        Gambar = new javax.swing.JLabel();
        SoundStatus = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        OpenWebcam.setText("Open Webcam");
        OpenWebcam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenWebcamActionPerformed(evt);
            }
        });

        Exit.setText("Exit");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });

        Start.setText("Start");
        Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartActionPerformed(evt);
            }
        });

        Stop.setText("Stop");
        Stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopActionPerformed(evt);
            }
        });

        Status.setText("Status");

        SoundStatus.setSelected(true);
        SoundStatus.setText("Sound");
        SoundStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SoundStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(SoundStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Start, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Stop, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(OpenWebcam, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Gambar, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 4, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Status)
                    .addComponent(OpenWebcam))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Gambar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 482, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Start, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Stop, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SoundStatus))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OpenWebcamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenWebcamActionPerformed
        openCamera();
    }//GEN-LAST:event_OpenWebcamActionPerformed
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        setTitle("Server");
        Status.setText("Running");
        Start.setEnabled(false);
        Stop.setEnabled(true);
    }//GEN-LAST:event_formWindowOpened
    
    public class Controller extends Thread{
        @Override
        public void run() {
            while (sendPic) {            
                captureCamera();
                sendCaptureImage();
//                DeleteFile();
                receiveImage();
            }
        }
    }
    
    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        if (sendPic) {
            sendPic = false;
            if (sendPic = false) {
                exitApp();
            }
            else{System.out.println("Work Berjalan");}
        }
        else{
            exitApp();
        }
    }//GEN-LAST:event_ExitActionPerformed

    private void StartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartActionPerformed
        initPicture();
        Status.setText("Running");
        Start.setEnabled(false);
        Stop.setEnabled(true);
    }//GEN-LAST:event_StartActionPerformed

    private void StopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopActionPerformed
        sendPic = false;
        Status.setText("Stopped");
        Start.setEnabled(true);
        Stop.setEnabled(false);
    }//GEN-LAST:event_StopActionPerformed

    private void SoundStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SoundStatusActionPerformed
        if (SoundStatus.isSelected()==true) {
            initAudio();
        } else {
            TakePicture.calling = false;
        }
    }//GEN-LAST:event_SoundStatusActionPerformed
    
    public void openCamera(){
        CvCapture capture = cvCreateCameraCapture(0);
        cvSetCaptureProperty(capture, CV_CAP_PROP_FRAME_HEIGHT, 720);
        cvSetCaptureProperty(capture, Videoio.CV_CAP_PROP_FRAME_WIDTH, 1280);
        IplImage grabbedImage = cvQueryFrame(capture);
        CanvasFrame frame = new CanvasFrame("Webcam");
        while (frame.isVisible() && (grabbedImage = cvQueryFrame(capture)) != null) {
            frame.showImage(grabbedImage);
        }
    }
    
    public void captureCamera(){
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        try {
            grabber.start();
            IplImage img = grabber.grab();
            if (img!=null) {
                cvSaveImage("capture.jpg", img);
            }
            grabber.release();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendCaptureImage(){
        try{
            BufferedImage img = ImageIO.read(new File("/media/budi/Dell_Data1/Documents/PemrogramanJaringan/UDP/capture.jpg"));
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
        File file = new File("/media/budi/Dell_Data1/Documents/PemrogramanJaringan/UDP/capture.jpg");
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
    
    public void exitApp(){
        String pesan;
        try{
            InetAddress ia = InetAddress.getByName(ipPublic);
            pesan = "stop";
            byte[] b = pesan.getBytes();
            DatagramPacket dp = new DatagramPacket(b, b.length, ia, portSend);
            DatagramSocket sender = new DatagramSocket();
            sender.send(dp);
            sender.close(); 
            System.out.println("pesan = " + pesan);
        }
        catch(Exception e){
            System.out.println(e);
        }
        System.exit(0);
    }
    
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
                ImageIcon set = new ImageIcon(img);
              
                Gambar.setIcon(set);
                clientsocket.close();
            }
        } catch (Exception e) {
                e.printStackTrace();
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
            java.util.logging.Logger.getLogger(TakePicture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TakePicture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TakePicture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TakePicture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TakePicture().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Exit;
    private javax.swing.JLabel Gambar;
    private javax.swing.JButton OpenWebcam;
    private javax.swing.JCheckBox SoundStatus;
    private javax.swing.JButton Start;
    private javax.swing.JLabel Status;
    private javax.swing.JButton Stop;
    // End of variables declaration//GEN-END:variables
}
