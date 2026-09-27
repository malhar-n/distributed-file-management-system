package com.distrifs.storage;

import com.distrifs.model.NodeRegistration;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintWriter;
import java.net.Socket;

public class StorageNodeServer {

    private static final String NODE_ID = "node-01";
    private static final String MASTER_HOST = "localhost";
    private static final int MASTER_PORT = 5000;
    private static final int NODE_PORT = 6001;

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       DistriFS Storage Node");
        System.out.println("=================================");

        NodeRegistration registration =
                new NodeRegistration(
                        NODE_ID,
                        "localhost",
                        NODE_PORT
                );

        try (
                Socket socket = new Socket(
                        MASTER_HOST,
                        MASTER_PORT
                );

                PrintWriter writer =
                        new PrintWriter(
                                socket.getOutputStream(),
                                true
                        )
        ) {

            ObjectMapper mapper = new ObjectMapper();

            String message = mapper.writeValueAsString(
                    registration
            );

            writer.println(message);

            System.out.println(
                    "Registration sent:"
            );

            System.out.println(message);

        } catch (Exception e) {

            System.err.println(
                    "Could not register with Master Server: "
                            + e.getMessage()
            );
        }
    }
}