package com.distrifs.master;

import com.distrifs.model.NodeRegistration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NodeRegistry {

    private final Map<String, NodeRegistration> nodes =
            new ConcurrentHashMap<>();

    public void registerNode(NodeRegistration node) {

        nodes.put(node.getNodeId(), node);

        System.out.println(
                "Node added to registry: "
                        + node.getNodeId()
        );
    }

    public NodeRegistration getNode(String nodeId) {
        return nodes.get(nodeId);
    }

    public void removeNode(String nodeId) {

        nodes.remove(nodeId);

        System.out.println(
                "Node removed from registry: "
                        + nodeId
        );
    }

    public void printNodes() {

        System.out.println("\n========== NODE REGISTRY ==========");

        if (nodes.isEmpty()) {
            System.out.println("No nodes registered.");
        }

        for (NodeRegistration node : nodes.values()) {

            System.out.println(
                    node.getNodeId()
                            + " -> "
                            + node.getHost()
                            + ":"
                            + node.getPort()
            );
        }

        System.out.println("====================================\n");
    }
}