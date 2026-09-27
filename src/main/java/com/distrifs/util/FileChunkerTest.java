package com.distrifs.util;

import com.distrifs.model.FileChunk;

import java.nio.file.Path;
import java.util.List;

public class FileChunkerTest {

    public static void main(String[] args) {

        Path file =
        Path.of("test-5mb.bin");

        FileChunker chunker =
                new FileChunker();

        try {

            List<FileChunk> chunks =
                    chunker.splitFile(file);

            System.out.println(
                    "\n========== FILE CHUNKS =========="
            );

            System.out.println(
                    "Total chunks: "
                            + chunks.size()
            );

            for (FileChunk chunk : chunks) {

                System.out.println(
                        chunk
                );
            }

            System.out.println(
                    "=================================\n"
            );

        } catch (Exception e) {

            System.err.println(
                    "Chunking failed: "
                            + e.getMessage()
            );
        }
    }
}
