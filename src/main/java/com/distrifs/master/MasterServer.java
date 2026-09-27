package com.distrifs.master;
import com.distrifs.database.NodeRepository;
import com.distrifs.model.Heartbeat;
import com.distrifs.model.NodeRegistration;
import com.fasterxml.jackson.databind.JsonNode;
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

        NodeRepository nodeRepository =
        new NodeRepository();

NodeRegistry nodeRegistry =
        new NodeRegistry(
                nodeRepository
        );

        Thread failureDetector =
        new Thread(
                new FailureDetector(nodeRegistry),
                "FailureDetector"
        );

failureDetector.start();

        ObjectMapper mapper =
                new ObjectMapper();

        try (
                ServerSocket serverSocket =
                        new ServerSocket(PORT)
        ) {

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
                        "\nNew storage node connection: "
                                + socket.getInetAddress()
                );

                Thread nodeHandler =
                        new Thread(
                                () -> handleNode(
                                        socket,
                                        mapper,
                                        nodeRegistry
                                )
                        );

                nodeHandler.start();
            }

        } catch (IOException e) {

            System.err.println(
                    "Master Server error: "
                            + e.getMessage()
            );
        }
    }

    private static void handleNode(
            Socket socket,
            ObjectMapper mapper,
            NodeRegistry nodeRegistry) {

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()
                                )
                        )
        ) {

            String message;

            while ((message = reader.readLine()) != null) {

                JsonNode json =
                        mapper.readTree(message);

                // Registration message
                if (json.has("port")) {

                    NodeRegistration node =
                            mapper.treeToValue(
                                    json,
                                    NodeRegistration.class
                            );

                    nodeRegistry.registerNode(node);

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

                // Heartbeat message
                else if (json.has("timestamp")) {

                    Heartbeat heartbeat =
                            mapper.treeToValue(
                                    json,
                                    Heartbeat.class
                            );

                    nodeRegistry.updateHeartbeat(
        heartbeat.getNodeId()
);

System.out.println(
        "Heartbeat received from "
                + heartbeat.getNodeId()
);
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Storage node connection lost: "
                            + e.getMessage()
            );

        } finally {

            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}