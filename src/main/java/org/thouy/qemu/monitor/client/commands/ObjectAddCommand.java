package org.thouy.qemu.monitor.client.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.thouy.qemu.monitor.client.model.QemuMonitorResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObjectAddCommand extends QemuMonitorCommand<ObjectAddCommand.Arguments, ObjectAddCommand.Response> {

    @Builder
    public static class Arguments {
        @JsonProperty("id") public String id;
        @JsonProperty("qom-type") public String qomType;
        @JsonProperty("size") public long size;
    }

    public static class Response extends QemuMonitorResponse<Void> {
    }

    public ObjectAddCommand(Arguments arguments) {
        super("object_add", arguments, Response.class);
    }
}
