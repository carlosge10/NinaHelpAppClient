package com.example.carlos.ninahelphandyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Help extends AppCompatActivity {

    private Spinner spinner1;
    private Socket socket;
    private static final int SERVER_PORT = 6969;
    private static final String SERVER_IP = "192.168.100.48";
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        tv = (TextView) findViewById(R.id.statusText);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
    }


    public void onClick(View v)
    {
        try {
            new Thread(new ClientThread()).start();
            tv.setText("Help sent!");
            new Thread(new ThreeSecondChange()).start();
        }
        catch(Exception e)
        {
            Log.e("valio cake", e.getMessage());
        }
    }

/*    public void onClick2(View v)
    {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.print("aiuda");

            tv.setText("Help sent!");
        }
        catch(UnknownHostException e)
        { e.printStackTrace(); }
        catch(IOException e)
        { e.printStackTrace(); }
        catch(Exception e)
        { e.printStackTrace(); }
    }
*/
    class ClientThread implements Runnable {
        @Override
        public void run() {
            DatagramSocket udpSocket = null;

            try {
                udpSocket = new DatagramSocket(SERVER_PORT);
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                byte[] buf = (String.valueOf(spinner1.getSelectedItem())).getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length,serverAddr, SERVER_PORT);
                udpSocket.send(packet);
            } catch (SocketException e) {
                Log.e("Udp:", "Socket Error:", e);
            } catch (IOException e) {
                Log.e("Udp Send:", "IO Error:", e);
            }
            finally {
                udpSocket.close();
            }
        }
    }
/*
    class ClientThread2 implements Runnable {
        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVER_PORT);
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
*/
    class ThreeSecondChange implements Runnable
    {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                changeText();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void changeText()
    {
        //((TextView) findViewById(R.id.statusText)).setText("Help Me World!");
    }
}
