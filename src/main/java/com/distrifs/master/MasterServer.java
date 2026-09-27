package com.distrifs.master;

import com.distrifs.model.NodeRegistration;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MasterServer {

    private static final int PORT = 5000;

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       DistriFS Master Server");
        System.out.println("=================================");

        ObjectMapper mapper = new ObjectMapper();

        try (ServerSocket serverSocket =
                     new ServerSocket(PORT)) {

            System.out.println(
                    "Master Server started."
            );

            System.out.println(
                    "Listening on port: " + PORT
            );

            System.out.println(
                    "Waiting for storage nodes..."
            );

            while (true) {

                Socket socket =
                        serverSocket.accept();

                System.out.println(
                        "\nStorage node connected from "
                                + socket.getInetAddress()
                );

                try (
                        BufferedReader reader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                socket.getInputStream()
                                        )
                                )
                ) {

                    String message =
                            reader.readLine();

                    NodeRegistration node =
                            mapper.readValue(
                                    message,
                                    NodeRegistration.class
                            );

                    System.out.println(
                            "Node registered successfully!"
                    );

                    System.out.println(
                            "Node ID : "
                                    + node.getNodeId()
                    );

                    System.out.println(
                            "Host    : "
                                    + node.getHost()
                    );

                    System.out.println(
                            "Port    : "
                                    + node.getPort()
                    );
                }

                socket.close();
            }

        } catch (IOException e) {

            System.err.println(
                    "Master Server error: "
                            + e.getMessage()
            );
        }
    }
}