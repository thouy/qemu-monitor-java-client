package org.thouy.qemu.monitor.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class QemuMonitorCommand<Argument, Response extends QemuMonitorResponse<?>> {

    @JsonProperty("execute") private final String command;
    @JsonProperty("arguments") private final Argument argument;
    @JsonIgnore private final Class<Response> responseType;

}
