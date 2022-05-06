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
            final int port = 6666;
            System.out.println("The server has just started on the port " + port);
            System.out.println("Waiting for clients...");
            try (ServerSocket serverSocket = new ServerSocket(port);
                 Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                System.out.println("A new client has just connected!");

                String line = in.readLine();
                System.out.println("Got line from client: " + line);

                StringBuilder sbLine = new StringBuilder(line);

                String reverse = sbLine.reverse().toString();

                System.out.println("Reversed line: " + reverse);
                out.println(reverse);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Goodbye!");
            }
        }
    }

    public static class Client {
        public static void main(String[] args) {

            String url = "localhost";
            int port = 6666;

            System.out.println("Trying to connect...");
            try (Socket clientSocket = new Socket(url, port)) {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("Connected!");

                String request = "Hello, I'm a client!";
                System.out.println("Sent a request: " + request);
                out.println(request);

                String response = in.readLine();
                System.out.println("Got a response: " + response);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Goodbye!");
            }
        }
    }
}