package com.thouy.qemu.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parent class that defines the basic structure of QMP command execution result statement
 * @param <Result> : Result type for QMP command result message.
 *           QMP command result message come in various forms, they must be defined separately for each QMP commands, so they are processed as Generic types.
 *           Generic types are defined in child class inherited QemuMonitorResponse class.
 */
@JsonInclude(Include.NON_NULL)
public class QemuMonitorResponse<Result> {

    @JsonProperty("return") private Result _return;
    @JsonProperty("error") private QemuMonitorError error;

    public Result getResult() {
        return _return;
    }

    public QemuMonitorError getError() {
        return error;
    }

    public boolean isError() {
        return error != null;
    }



}
