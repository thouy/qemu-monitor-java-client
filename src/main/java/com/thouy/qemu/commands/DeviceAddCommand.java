package com.thouy.qemu.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import com.thouy.qemu.model.QemuMonitorResponse;

public class DeviceAddCommand extends QemuMonitorCommand<DeviceAddCommand.Arguments, DeviceAddCommand.Response> {

    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Arguments {
        @JsonProperty("id") public String id;
        @JsonProperty("driver") public String driver;
        @JsonProperty("socket-id") public Integer socketId;
        @JsonProperty("core-id") public Integer coreId;
        @JsonProperty("thread-id") public Integer threadId;
        @JsonProperty("memdev") public String memdev;
    }

    public static class Response extends QemuMonitorResponse<Void> {
    }

    public DeviceAddCommand(Arguments arguments) {
        super("device_add", arguments, Response.class);
    }


}
