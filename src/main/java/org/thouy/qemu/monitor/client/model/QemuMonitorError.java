package org.thouy.qemu.monitor.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {"error": {"class": "GenericError", "desc": "CPU[0] with APIC ID 0 exists"}}
 *
 */
public class QemuMonitorError {
    @JsonProperty("class") public String _class;
    @JsonProperty("desc") public String desc;
}
