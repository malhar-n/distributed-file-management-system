package com.distrifs.model;

public class FileChunk {

    private final int chunkIndex;
    private final long size;
    private final String hash;

    public FileChunk(
            int chunkIndex,
            long size,
            String hash) {

        this.chunkIndex = chunkIndex;
        this.size = size;
        this.hash = hash;
    }

    public int getChunkIndex() {
        return chunkIndex;
    }

    public long getSize() {
        return size;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {

        return "FileChunk{" +
                "chunkIndex=" + chunkIndex +
                ", size=" + size +
                ", hash='" + hash + '\'' +
                '}';
    }
}