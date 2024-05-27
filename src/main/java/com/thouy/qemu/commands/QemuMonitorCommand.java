package com.thouy.qemu.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.thouy.qemu.model.QemuMonitorResponse;


/**
 *  Parent class that defines the basic structure of QMP command
 *  > e.g.
 *      {"execute": "device_add", "arguments": {...}}
 *      {"execute": "device_del", "arguments": {"id": "dimm3"}}
 *      {"execute": "object-add", "arguments": {...}}
 *
 * @param <Argument> Generic Type. Detailed definitions are implemented in each command class.
 * @param <Response> Generic Type. Detailed definitions are implemented in each command class.
 */
@Getter
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class QemuMonitorCommand<Argument, Response extends QemuMonitorResponse<?>> {

    @JsonProperty("execute") private final String command;
    @JsonProperty("arguments") private final Argument argument;
    @JsonIgnore private final Class<Response> responseType;

}
