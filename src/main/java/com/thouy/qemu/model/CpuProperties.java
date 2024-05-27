package com.thouy.qemu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


/**
 *  Defined response JSON string about hot pluggable vCPU properties
 *  e.g. {
 *          "socket-id": 7
 *          "core-id": 0,
 *          "thread-id": 0,
 *          "node-id": 0,
 *       }
 */
@Getter
public class CpuProperties {

    @JsonProperty("socket-id") private int socketId;
    @JsonProperty("core-id") private int coreId;
    @JsonProperty("thread-id") private int threadId;
    @JsonProperty("node-id") private int nodeId;



}
