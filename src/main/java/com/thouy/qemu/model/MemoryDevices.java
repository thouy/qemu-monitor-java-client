package com.thouy.qemu.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


/**
 *  Defined response JSON string about memory devices
 *  e.g. {
 *          "type": "dimm1",
 *          "data": {
 *              "memdev": "/objects/mem1",
 *              "hotplugged": true,
 *              "addr": 4294967296,
 *              "hotpluggable": true,
 *              "size": 1073741824,
 *              "slot": 0,
 *              "node": 0,
 *              "id": "dimm1"
 *          }
 *       }
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemoryDevices {

    @JsonProperty("type") private String type;
    @JsonProperty("data") private MemoryProperties data;
}
