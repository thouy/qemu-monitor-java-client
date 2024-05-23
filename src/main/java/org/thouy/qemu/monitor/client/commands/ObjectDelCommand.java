package org.thouy.qemu.monitor.client.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.thouy.qemu.monitor.client.model.QemuMonitorResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObjectDelCommand extends QemuMonitorCommand<ObjectDelCommand.Arguments, ObjectDelCommand.Response> {

    @Builder
    public static class Arguments {
        @JsonProperty("id") public String id;
    }

    public static class Response extends QemuMonitorResponse<Void> {
    }

    public ObjectDelCommand(Arguments arguments) {
        super("object-del", arguments, Response.class);
    }
}
