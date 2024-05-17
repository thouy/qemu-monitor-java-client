package org.thouy.qemu.monitor.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;


/**
 *  Defined response JSON string about hot pluggable vCPU
 *  e.g. {
 *          "props": {"core-id": 0, "thread-id": 0, "node-id": 0, "socket-id": 7},
 *          "vcpus-count": 1,
 *          "qom-path": "/machine/unattached/device[0]",
 *          "type": "host-x86_64-cpu"
 *       }
 */
@JsonInclude(Include.NON_NULL)
public class HotpluggableCpus {

    @JsonProperty("props") public CpuProperties props;
    @JsonProperty("vcpus-count") public int vcpusCount;
    @JsonProperty("qom-path") public String gomPath;
    @JsonProperty("type") public String type;
}
