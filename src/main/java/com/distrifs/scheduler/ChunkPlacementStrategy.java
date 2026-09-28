package com.distrifs.scheduler;

import com.distrifs.model.FileChunk;
import com.distrifs.model.NodeRegistration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ChunkPlacementStrategy {

    public Map<Integer, NodeRegistration> placeChunks(
            List<FileChunk> chunks,
            List<NodeRegistration> nodes) {

        if (nodes.isEmpty()) {
            throw new IllegalArgumentException(
                    "No storage nodes available."
            );
        }

        Map<Integer, NodeRegistration> placement =
                new LinkedHashMap<>();

        for (int i = 0; i < chunks.size(); i++) {

            FileChunk chunk = chunks.get(i);

            NodeRegistration node =
                    nodes.get(i % nodes.size());

            placement.put(
                    chunk.getChunkIndex(),
                    node
            );
        }

        return placement;
    }
}