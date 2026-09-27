package com.distrifs.storage;

import com.distrifs.model.Heartbeat;
import com.distrifs.model.NodeRegistration;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintWriter;
import java.net.Socket;

public class StorageNodeServer {

    private static final String MASTER_HOST = "localhost";
private static final int MASTER_PORT = 5000;
private static final long HEARTBEAT_INTERVAL_MS = 5000;

    public static void main(String[] args) {

        if (args.length != 2) {

    System.out.println(
            "Usage: StorageNodeServer <node-id> <port>"
    );

    return;
}

String nodeId = args[0];

int nodePort;

try {

    nodePort = Integer.parseInt(args[1]);

} catch (NumberFormatException e) {

    System.err.println(
            "Port must be a valid number."
    );

    return;
}

        System.out.println("=================================");
        System.out.println("       DistriFS Storage Node");
        System.out.println("=================================");

        ObjectMapper mapper = new ObjectMapper();

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

            // -------------------------------
            // 1. Register with Master
            // -------------------------------

            NodeRegistration registration =
                    new NodeRegistration(
                            nodeId,
                            "localhost",
                            nodePort
                    );

            String registrationMessage =
                    mapper.writeValueAsString(registration);

            writer.println(registrationMessage);

            System.out.println(
                    "Registration sent:"
            );

            System.out.println(
                    registrationMessage
            );

            // -------------------------------
            // 2. Start heartbeat loop
            // -------------------------------

            System.out.println(
                    "\nHeartbeat service started."
            );

            while (true) {

                Heartbeat heartbeat =
                        new Heartbeat(
                                nodeId,
                                System.currentTimeMillis()
                        );

                String heartbeatMessage =
                        mapper.writeValueAsString(
                                heartbeat
                        );

                writer.println(
                        heartbeatMessage
                );

                System.out.println(
                        "Heartbeat sent: "
                                + heartbeatMessage
                );

                Thread.sleep(
                        HEARTBEAT_INTERVAL_MS
                );
            }

        } catch (Exception e) {

            System.err.println(
                    "Storage node stopped: "
                            + e.getMessage()
            );
        }
    }
}