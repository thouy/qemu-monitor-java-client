package com.thouy.qemu.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thouy.qemu.model.QemuMonitorResponse;
import lombok.Builder;

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
