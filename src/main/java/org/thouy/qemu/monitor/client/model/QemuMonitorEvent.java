package org.thouy.qemu.monitor.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * {"timestamp": {"seconds": 1715309958, "microseconds": 801037}, "event": "ACPI_DEVICE_OST", "data": {"info": {"device": "hotplug-cpu-1", "source": 1, "status": 0, "slot": "1", "slot-type": "CPU"}}}
 * {"timestamp": {"seconds": 1715310546, "microseconds": 947507}, "event": "DEVICE_DELETED", "data": {"path": "/machine/peripheral/hotplug-cpu-1/lapic"}}
 * {"timestamp": {"seconds": 1715310546, "microseconds": 947636}, "event": "DEVICE_DELETED", "data": {"device": "hotplug-cpu-1", "path": "/machine/peripheral/hotplug-cpu-1"}}
 * {"timestamp": {"seconds": 1715310546, "microseconds": 947765}, "event": "ACPI_DEVICE_OST", "data": {"info": {"source": 3, "status": 0, "slot": "1", "slot-type": "CPU"}}}
 *
 */
public class QemuMonitorEvent {

    public static class Timestamp {
        @JsonProperty
        public long seconds;
        @JsonProperty
        public long microseconds;
    }

    @JsonProperty
    public Timestamp timestamp;
    @JsonProperty
    public String event;


}
