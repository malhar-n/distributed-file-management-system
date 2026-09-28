package com.distrifs.scheduler;

import com.distrifs.model.FileChunk;
import com.distrifs.model.NodeRegistration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChunkPlacementStrategy {

    public Map<Integer, NodeRegistration> placeChunks(
            List<FileChunk> chunks,
            List<NodeRegistration> nodes,
            Set<String> unavailableNodeIds) {

        List<NodeRegistration> healthyNodes =
                new ArrayList<>();

        for (NodeRegistration node : nodes) {

            if (!unavailableNodeIds.contains(
                    node.getNodeId())) {

                healthyNodes.add(node);
            }
        }

        if (healthyNodes.isEmpty()) {

            throw new IllegalStateException(
                    "No healthy storage nodes available."
            );
        }

        Map<Integer, NodeRegistration> placement =
                new LinkedHashMap<>();

        for (int i = 0; i < chunks.size(); i++) {

            FileChunk chunk =
                    chunks.get(i);

            NodeRegistration node =
                    healthyNodes.get(
                            i % healthyNodes.size()
                    );

            placement.put(
                    chunk.getChunkIndex(),
                    node
            );
        }

        return placement;
    }
}