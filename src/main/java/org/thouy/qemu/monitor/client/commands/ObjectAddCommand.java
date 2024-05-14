package org.thouy.qemu.monitor.client.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.thouy.qemu.monitor.client.model.QemuMonitorCommand;
import org.thouy.qemu.monitor.client.model.QemuMonitorResponse;

public class ObjectAddCommand extends QemuMonitorCommand<ObjectAddCommand.Arguments, ObjectAddCommand.Response> {

    public static class Arguments {
        @JsonProperty("id") public String id;
        @JsonProperty("qom-type") public String qomType;

        @JsonProperty("size") public long size;

        public Arguments(String id, String qomType, long size) {
            this.id = id;
            this.qomType = qomType;
            this.size = size;
        }

    }

    public static class Response extends QemuMonitorResponse<Void> {
    }

    public ObjectAddCommand(Arguments arguments) {
        super("object_add", arguments, Response.class);
    }
}
