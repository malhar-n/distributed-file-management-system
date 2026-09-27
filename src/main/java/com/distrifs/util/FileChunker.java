package com.distrifs.util;

import com.distrifs.model.FileChunk;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class FileChunker {

    private static final int CHUNK_SIZE =
            1024 * 1024; // 1 MB

    public List<FileChunk> splitFile(
            Path filePath) throws IOException {

        List<FileChunk> chunks =
                new ArrayList<>();

        try (
                InputStream inputStream =
                        Files.newInputStream(filePath)
        ) {

            byte[] buffer =
                    new byte[CHUNK_SIZE];

            int bytesRead;
            int chunkIndex = 0;

            while (
                    (bytesRead =
                            inputStream.read(buffer))
                            != -1
            ) {

                String hash =
                        calculateHash(
                                buffer,
                                bytesRead
                        );

                FileChunk chunk =
                        new FileChunk(
                                chunkIndex,
                                bytesRead,
                                hash
                        );

                chunks.add(chunk);

                chunkIndex++;
            }
        }

        return chunks;
    }

    private String calculateHash(
            byte[] data,
            int length) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance(
                            "SHA-256"
                    );

            digest.update(
                    data,
                    0,
                    length
            );

            byte[] hashBytes =
                    digest.digest();

            StringBuilder hash =
                    new StringBuilder();

            for (byte b : hashBytes) {

                hash.append(
                        String.format(
                                "%02x",
                                b
                        )
                );
            }

            return hash.toString();

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(
                    "SHA-256 algorithm not available.",
                    e
            );
        }
    }
}