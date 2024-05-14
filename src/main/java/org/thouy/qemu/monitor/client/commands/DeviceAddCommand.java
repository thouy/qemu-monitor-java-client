package org.thouy.qemu.monitor.client.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.thouy.qemu.monitor.client.model.QemuMonitorCommand;
import org.thouy.qemu.monitor.client.model.QemuMonitorResponse;

public class DeviceAddCommand extends QemuMonitorCommand<DeviceAddCommand.Arguments, DeviceAddCommand.Response> {

    public static class Arguments {
        @JsonProperty("id") public String id;
        @JsonProperty("driver") public String driver;
        @JsonProperty("socket-id") public int socketId;
        @JsonProperty("memdev") public String memdev;

        public Arguments(String id, String driver, int socketId) {
            this.id = id;
            this.driver = driver;
            this.socketId = socketId;
        }

        public Arguments(String id, String driver, String memdev) {
            this.id = id;
            this.driver = driver;
            this.memdev = memdev;
        }
    }

    public static class Response extends QemuMonitorResponse<Void> {
    }

    public DeviceAddCommand(Arguments arguments) {
        super("device_add", arguments, Response.class);
    }


}
