package com.afs.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Class for handling the iso message request and responses.
 */
public class ISO8583Client {

    private Logger log = LoggerFactory.getLogger(getClass());
    private static final String host = "localhost";
    private static final int port = 5000;

    public ISO8583Client(String input) {

        Socket socket = null;
        try {
//            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
//            String input = inFromUser.readLine();
            log.info(" ISO8583 Data : {}", input);

            socket = new Socket(host, port);
            clientHandler(socket, input.getBytes());
        } catch (IOException e) {
            log.error("Couldn't create Socket", e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    log.error("Couldn't close the Socket", e);
                }
            }
        }
    }

    /**
     * Handle the iso8583 message request and responses

     * @param connection  Socket connection with backend Test server
     * @param isoMessage  packed ISOMessage
     */

    private void clientHandler(Socket connection, byte[] isoMessage) {

        DataOutputStream outStream = null;
        BufferedReader inFromServer = null;
        try {
            outStream = new DataOutputStream(connection.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (connection.isConnected()) {
                outStream.write(isoMessage);
                outStream.flush();

                /* Sender will receive the Acknowledgement here */
                log.info("Response From Server :" + inFromServer.readLine());
            }
        } catch (IOException e) {
            log.error("An exception occurred in sending the iso8583 message", e);
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
                if (inFromServer != null) {
                    inFromServer.close();
                }
            } catch (IOException e) {
                log.error("Couldn't close the I/O Streams", e);
            }
        }
    }

    public static void main(String[] args) {

        new ISO8583Client("0200B220000100100000000000000002000020134500000050000001115221801234890610000914XYRTUI5269TYUI021ABCDEFGHIJ 1234567890");
    }
}