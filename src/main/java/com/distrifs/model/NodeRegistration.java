package com.distrifs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NodeRegistration {

    private final String nodeId;
    private final String host;
    private final int port;

    @JsonCreator
    public NodeRegistration(
            @JsonProperty("nodeId") String nodeId,
            @JsonProperty("host") String host,
            @JsonProperty("port") int port) {

        this.nodeId = nodeId;
        this.host = host;
        this.port = port;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "NodeRegistration{" +
                "nodeId='" + nodeId + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}