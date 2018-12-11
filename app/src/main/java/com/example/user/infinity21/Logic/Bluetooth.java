package com.example.user.infinity21.Logic;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by User on 16/03/2018.
 */

public class Bluetooth {


    private BluetoothAdapter bluetoothAdapter;
    private Context context;
    private AcceptThread acceptThread;

    private ConnectThread connectThread;
    private BluetoothDevice device;
    private UUID deviceUUID;

    private ConnectedThread connectedThread;

    public Bluetooth(Context context) {
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.context = context;
        start();
    }

    private class AcceptThread extends Thread{
        private BluetoothServerSocket bluetoothServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            try {
                Toast.makeText(context, "Accept: setting", Toast.LENGTH_SHORT).show();
                tmp = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(Utility.APPNAME, Utility.MY_UUID);
            }catch (Exception e){

            }
            bluetoothServerSocket = tmp;
        }

        public void run(){
            Toast.makeText(context, "Accept: running", Toast.LENGTH_SHORT).show();
            BluetoothSocket bluetoothSocket = null;
            try {
                Toast.makeText(context, "Accept: Run: start", Toast.LENGTH_SHORT).show();
                bluetoothSocket = bluetoothServerSocket.accept();
                Toast.makeText(context, "Accept: connecting", Toast.LENGTH_SHORT).show();
            }catch(Exception e){

            }

            if(bluetoothSocket != null){
                connected(bluetoothSocket,device);
            }
        }

        public void cancel(){
            try{
                bluetoothServerSocket.close();
            }catch (Exception e){

            }
        }
    }


    private class ConnectThread extends  Thread{
        private BluetoothSocket connectSocket;

        public ConnectThread(BluetoothDevice tmpDevice, UUID uuid){
            Toast.makeText(context, "Connect: started", Toast.LENGTH_SHORT).show();
            device = tmpDevice;
            deviceUUID = uuid;
        }

        public void run() {
            BluetoothSocket tmp = null;
            Toast.makeText(context, "Connect: Run: start", Toast.LENGTH_SHORT).show();
            try {
                tmp = device.createInsecureRfcommSocketToServiceRecord(deviceUUID);
            }catch (Exception e){

            }
            connectSocket = tmp;

            bluetoothAdapter.cancelDiscovery();

            try {
                connectSocket.connect();
                Toast.makeText(context, "Connect: Run: connected", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                try {
                    connectSocket.close();
                    Toast.makeText(context, "Connect: Run: close", Toast.LENGTH_SHORT).show();
                } catch (IOException e1) {

                }
                Toast.makeText(context, "Connect: Run: fail", Toast.LENGTH_SHORT).show();

            }

            connected(connectSocket,device);
        }
        public void cancel(){
            try{
                connectSocket.close();
            }catch (Exception e){

            }
        }
    }

    public synchronized void start(){
        Toast.makeText(context, "Start:", Toast.LENGTH_SHORT).show();

        if(connectThread != null){
            connectThread.cancel();
            connectThread = null;
        }

        if(acceptThread == null){
            acceptThread = new AcceptThread();
            acceptThread.start();
        }

    }

    public void startClient(BluetoothDevice tmpDevice, UUID uuid){
        connectThread = new ConnectThread(tmpDevice,uuid);
        connectThread.start();
    }

    private class ConnectedThread extends Thread{
        private BluetoothSocket connectedSocket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public ConnectedThread(BluetoothSocket socket){
            connectedSocket = socket;
            InputStream in = null;
            OutputStream out = null;
            try {
                in = connectedSocket.getInputStream();
                out = connectedSocket.getOutputStream();
            }catch (Exception e){

            }
            inputStream = in;
            outputStream = out;
        }

        public void run(){
            byte[] buffer = new byte[1024];
            int bytes;

            while(true){
                try {
                    bytes = inputStream.read(buffer);
                } catch (IOException e) {
                    break;
                }
                String incomingMassage = new String(buffer,0,bytes);

            }

        }
        public void write(byte[] bytes){
            String text = new String(bytes, Charset.defaultCharset());
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void cancel(){
            try {
                connectedSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void connected(BluetoothSocket bluetoothSocket, BluetoothDevice device) {
        connectedThread = new ConnectedThread(bluetoothSocket);
        connectedThread.start();
    }

    public void write(byte[] bytes){
        connectedThread.write(bytes);
    }
}
