package com.distrifs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Heartbeat {

    private final String nodeId;
    private final long timestamp;

    @JsonCreator
    public Heartbeat(
            @JsonProperty("nodeId") String nodeId,
            @JsonProperty("timestamp") long timestamp) {

        this.nodeId = nodeId;
        this.timestamp = timestamp;
    }

    public String getNodeId() {
        return nodeId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}