package portal.remote.superpeer.remote.com.androidsocketmessage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * * ============================================================================
 * * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * * Unauthorized copying of this file, via any medium is strictly prohibited
 * * Proprietary and confidential
 * * ----------------------------------------------------------------------------
 * * Created by: Mimo Saha on [03-Dec-2018 at 5:18 PM].
 * * Email: mimosaha@w3engineers.com
 * * ----------------------------------------------------------------------------
 * * Project: AndroidSocketMessage.
 * * Code Responsibility: <Purpose of code>
 * * ----------------------------------------------------------------------------
 * * Edited by :
 * * --> <First Editor> on [03-Dec-2018 at 5:18 PM].
 * * --> <Second Editor> on [03-Dec-2018 at 5:18 PM].
 * * ----------------------------------------------------------------------------
 * * Reviewed by :
 * * --> <First Reviewer> on [03-Dec-2018 at 5:18 PM].
 * * --> <Second Reviewer> on [03-Dec-2018 at 5:18 PM].
 * * ============================================================================
 **/
public class SocketParser {

    private static SocketParser socketParser = new SocketParser();
    private ServerSocket serverSocket;
    private Thread serverThread = null;
    private static final int PORT = 2165;

    private Socket socket;
    private InetAddress inetAddress;

//    private String socketIPAddress = "192.168.2.15";  // 38
    private String socketIPAddress = "192.168.2.110"; // 37

    private DataCallback dataCallback;

    public interface DataCallback {
        void processData(String data);
    }

    private SocketParser() {
        try {
            serverThread = new Thread(new ServerThread());
            serverThread.setDaemon(true);

            inetAddress = InetAddress.getByName(socketIPAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SocketParser setDataCallback(DataCallback dataCallback) {
        this.dataCallback = dataCallback;
        return this;
    }

    public static SocketParser getInstance() {
        return socketParser;
    }

    public void startProcess() {
        serverThread.start();
    }

    public void stopProcess() {
        serverThread.interrupt();
    }

    class ServerThread implements Runnable {

        public void run() {
            Socket socket = null;
            try {
                serverSocket = new ServerSocket(PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!serverThread.isInterrupted()) {
                try {
                    socket = serverSocket.accept();
                    CommunicationThread commThread = new CommunicationThread(socket);
                    new Thread(commThread).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CommunicationThread implements Runnable {

        private Socket clientSocket;
        private BufferedReader input;

        CommunicationThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                input = new BufferedReader(new
                        InputStreamReader(this.clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                String read = input.readLine();
                if (dataCallback != null) {
                    dataCallback.processData(read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void sendMessage(final String message) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(inetAddress, PORT);

                    PrintWriter out = new PrintWriter(new BufferedWriter(new
                            OutputStreamWriter(socket.getOutputStream())), true);

                    out.println(message);

                    if (socket != null)
                        socket.close();

                } catch (Exception e) {
                    System.err.print("Send Message Error: " + e);
                    e.printStackTrace();
                }
            }
        };

        new Thread(runnable).start();
    }

}
