package com.thouy.qemu.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


/**
 *  Defined response JSON string about memory properties
 *  e.g. {
 *          "id": "dimm1",
 *          "memdev": "/objects/mem1",
 *          "hotplugged": true,
 *          "hotpluggable": true,
 *          "addr": 4294967296,
 *          "slot": 0,
 *          "node": 0,
 *          "size": 1073741824
 *       }
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemoryProperties {

    @JsonProperty("id") private String deviceId;
    @JsonProperty("memdev") private String memdev;
    @JsonProperty("slot") private int slot;
    @JsonProperty("node") private int node;
    @JsonProperty("addr") private long addr;
    @JsonProperty("size") private long size;
    @JsonProperty("hotplugged") private boolean hotplugged;
    @JsonProperty("hotpluggable") private boolean hotpluggable;
}
