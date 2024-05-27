package com.thouy.qemu.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import com.thouy.qemu.model.QemuMonitorResponse;

public class DeviceDelCommand extends QemuMonitorCommand<DeviceDelCommand.Arguments, DeviceDelCommand.Response> {

    @Builder
    public static class Arguments {
        @JsonProperty("id") public String id;
    }

    public static class Response extends QemuMonitorResponse<Void> {
    }

    public DeviceDelCommand(Arguments arguments) {
        super("device_del", arguments, Response.class);
    }

}
