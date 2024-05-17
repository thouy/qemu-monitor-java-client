package org.thouy.qemu.monitor.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *  Defined response JSON string about hot pluggable vCPU properties
 *  e.g. {
 *          "socket-id": 7
 *          "core-id": 0,
 *          "thread-id": 0,
 *          "node-id": 0,
 *       }
 */
public class CpuProperties {

    @JsonProperty("socket-id") public int socketId;
    @JsonProperty("core-id") public int coreId;
    @JsonProperty("thread-id") public int threadId;
    @JsonProperty("node-id") public int nodeId;



}
