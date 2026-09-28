package com.distrifs.scheduler;

import com.distrifs.model.FileChunk;
import com.distrifs.model.NodeRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChunkPlacementTest {

    public static void main(String[] args) {

        List<FileChunk> chunks =
                new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            chunks.add(
                    new FileChunk(
                            i,
                            1024,
                            "hash-" + i
                    )
            );
        }

        List<NodeRegistration> nodes =
                List.of(
                        new NodeRegistration(
                                "node-01",
                                "localhost",
                                6001
                        ),

                        new NodeRegistration(
                                "node-02",
                                "localhost",
                                6002
                        ),

                        new NodeRegistration(
                                "node-03",
                                "localhost",
                                6003
                        )
                );

        ChunkPlacementStrategy strategy =
                new ChunkPlacementStrategy();

        Map<Integer, NodeRegistration> placement =
                strategy.placeChunks(
                        chunks,
                        nodes
                );

        System.out.println(
                "\n========== CHUNK PLACEMENT =========="
        );

        for (
                Map.Entry<Integer, NodeRegistration> entry
                        : placement.entrySet()
        ) {

            System.out.println(
                    "Chunk "
                            + entry.getKey()
                            + " → "
                            + entry.getValue().getNodeId()
            );
        }

        System.out.println(
                "=====================================\n"
        );
    }
}