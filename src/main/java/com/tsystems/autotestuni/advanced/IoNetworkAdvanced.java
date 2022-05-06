package com.tsystems.autotestuni.advanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class IoNetworkAdvanced {

    /**
     * There is {@link Server} and {@link Client} classes from the lesson.
     *
     * 1. Modify {@link Server} class not to finish its work when the client terminated the connection.
     * 2. Modify {@link Server} class to get its server port from CLI arguments.
     * 3. Run two instances of the server on different ports.
     * 4. Modify {@link Client} class to get two server ports.
     * 5. Run an instance of the client with two server ports above.
     * 5. Modify {@link Client} class to get connections to each of the service, then send a string
     *    to one, get back the reversed string and send it to another one to get the original string.
     */
    public static class Server {
        public static void main(String[] args) {

            if (args.length != 1) {
                System.out.println("Usage: java Server <port>");
                System.exit(1);
            }

            int port = 0;
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port: " + args[0]);
                System.exit(2);
            }

            System.out.println("The server has just started on the port " + port);
            System.out.println("Waiting for clients...");
            try (ServerSocket serverSocket = new ServerSocket(port)) {

                // Use infinite loop to serve all clients
                while (true) {
                    try (Socket clientSocket = serverSocket.accept();
                         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                        System.out.println("A new client has just connected!");

                        String line = in.readLine();
                        System.out.println("Got line from client: " + line);

                        StringBuilder sbLine = new StringBuilder(line);

                        String reverse = sbLine.reverse().toString();

                        System.out.println("Reversed line: " + reverse);
                        out.println(reverse);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Goodbye!");
            }
        }
    }

    public static class Client {
        public static void main(String[] args) {

            if (args.length != 2) {
                System.out.println("Usage: java Client <port1> <port2>");
                System.exit(1);
            }

            String url = "localhost";
            int port1 = 0;
            int port2 = 0;

            try {
                port1 = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port: " + args[0]);
                System.exit(2);
            }

            try {
                port2 = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port: " + args[1]);
                System.exit(2);
            }


            System.out.println("Trying to connect...");
            try (Socket client1Socket = new Socket(url, port1);
                 Socket client2Socket = new Socket(url, port2)) {

                String response1;

                // Communication with server 1
                try (PrintWriter out1 = new PrintWriter(client1Socket.getOutputStream(), true);
                     BufferedReader in1 = new BufferedReader(new InputStreamReader(client1Socket.getInputStream()))) {
                    System.out.println("Connected to server 1!");

                    String request = "Hello, I'm a client!";
                    System.out.println("Sent a request to server 1: " + request);
                    out1.println(request);
                    response1 = in1.readLine();
                    System.out.println("Got a response from server 1: " + response1);
                }

                // Communication with server 2
                try (PrintWriter out2 = new PrintWriter(client2Socket.getOutputStream(), true);
                     BufferedReader in2 = new BufferedReader(new InputStreamReader(client2Socket.getInputStream()))) {
                    System.out.println("Connected to server 2!");

                    System.out.println("Sent a request to server 2: " + response1);
                    out2.println(response1);
                    String response2 = in2.readLine();
                    System.out.println("Got a response from server 2: " + response2);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Goodbye!");
            }
        }
    }
}