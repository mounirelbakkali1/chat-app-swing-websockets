package chatting.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements ActionListener {
    
    static JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    
    Server() {
        
        f.setLayout(null);
        f.add(FonctionaliteCommain.header("server.png","Server"));

        FonctionaliteCommain.footer(f,a1,text,this,true);

        
        f.setSize(450, 700);
        f.setLocation(200, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        
        f.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText();
            JPanel p2 = FonctionaliteCommain.formatLabel(out,true);
            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);
            dout.writeUTF(out);
            text.setText("");
            f.repaint();
            f.invalidate();
            f.validate();   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        new Server();
        Thread serverThread = new Thread(new ServerThread());
        serverThread.start();
    }

    static class ServerThread implements Runnable {
        public void run() {
            try {
                ServerSocket skt = new ServerSocket(6001);
                while(true) {
                    Socket s = skt.accept();
                    DataInputStream din = new DataInputStream(s.getInputStream());
                    dout = new DataOutputStream(s.getOutputStream());
                    while(true) {
                        String msg = din.readUTF();
                        JPanel panel = FonctionaliteCommain.formatLabel(msg,false);
                        JPanel left = new JPanel(new BorderLayout());
                        left.add(panel, BorderLayout.LINE_START);
                        vertical.add(left);
                        f.validate();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
