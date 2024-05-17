package org.thouy.qemu.monitor.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *  Define QEMU version and capability info message
 *  > e.g. {"QMP": {"version": {"qemu": {"micro": 0, "minor": 2, "major": 7}, "package": "qemu-kvm-7.2.0-14.el9_2.5"}, "capabilities": ["oob"]}}
 */
public class QemuMonitorGreeting {

    @JsonProperty("QMP")
    public QMPCapabilities qmpCapabilities;

    public static class QMPCapabilities {

        @JsonProperty("version") public Version version;
        @JsonProperty("capabilities") public List<Object> capabilities;

        public static class Version {
            @JsonProperty("qemu") public VersionDetail versionDetail;
            @JsonProperty("package") public String _package;
            public static class VersionDetail {
                @JsonProperty("major") public int major;
                @JsonProperty("minor") public int minor;
                @JsonProperty("micro") public int micro;
            }
        }
    }

}
