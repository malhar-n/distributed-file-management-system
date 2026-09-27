package com.distrifs.master;
import com.distrifs.database.NodeRepository;

public class FailureDetector implements Runnable {

    private static final long CHECK_INTERVAL_MS = 2000;

    private static final long HEARTBEAT_TIMEOUT_MS = 10000;

    private final NodeRegistry nodeRegistry;

    private final NodeRepository nodeRepository;



    public FailureDetector(
        NodeRegistry nodeRegistry,
        NodeRepository nodeRepository) {

    this.nodeRegistry = nodeRegistry;
    this.nodeRepository = nodeRepository;
}

    @Override
    public void run() {

        System.out.println(
                "Failure detector started."
        );

        while (true) {

            try {

                Thread.sleep(
                        CHECK_INTERVAL_MS
                );

                checkNodes();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                System.out.println(
                        "Failure detector stopped."
                );

                break;
            }
        }
    }

    private void checkNodes() {

        long currentTime =
                System.currentTimeMillis();

        nodeRegistry.getNodes().forEach(
                (nodeId, node) -> {

                    long lastHeartbeat =
                            nodeRegistry
                                    .getLastHeartbeat(
                                            nodeId
                                    );

                    long elapsed =
                            currentTime
                                    - lastHeartbeat;

                    if (elapsed >
                            HEARTBEAT_TIMEOUT_MS) {

                        System.out.println(
                                "\n!!! NODE FAILURE DETECTED !!!"
                        );

                        System.out.println(
                                "Node: "
                                        + nodeId
                        );

                        System.out.println(
                                "Last heartbeat was "
                                        + elapsed
                                        + " ms ago."
                        );
                        try {

    nodeRepository.markNodeFailed(nodeId);

    System.out.println(
            "Node status updated to FAILED in database."
    );

} catch (Exception e) {

    System.err.println(
            "Failed to update node status in database: "
                    + e.getMessage()
    );
}
                        nodeRegistry.removeNode(
                                nodeId
                        );
                    }
                }
        );
    }
}
