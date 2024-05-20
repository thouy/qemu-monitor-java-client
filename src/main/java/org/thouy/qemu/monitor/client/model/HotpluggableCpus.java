package org.thouy.qemu.monitor.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Getter;


/**
 *  Defined response JSON string about hot pluggable vCPU
 *  e.g. {
 *          "props": {"core-id": 0, "thread-id": 0, "node-id": 0, "socket-id": 7},
 *          "vcpus-count": 1,
 *          "qom-path": "/machine/unattached/device[0]",
 *          "type": "host-x86_64-cpu"
 *       }
 */
@Getter
@JsonInclude(Include.NON_NULL)
public class HotpluggableCpus {

    @JsonProperty("props") private CpuProperties props;
    @JsonProperty("vcpus-count") private int vcpusCount;
    @JsonProperty("qom-path") private String qomPath;
    @JsonProperty("type") private String type;
}
