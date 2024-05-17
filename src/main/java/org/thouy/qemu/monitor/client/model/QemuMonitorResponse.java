package org.thouy.qemu.monitor.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parent class that defines the basic structure of QMP command execution result statement
 * @param <T> : Result type for QMP command result message.
 *           QMP command result message come in various forms, they must be defined separately for each QMP commands, so they are processed as Generic types.
 *           Generic types are defined in child class inherited QemuMonitorResponse class.
 */
public class QemuMonitorResponse<T> {

    @JsonProperty("return") public T _return;
    @JsonProperty("error") public QemuMonitorError error;

    public T getResult() {
        return _return;
    }

    public QemuMonitorError getError() {
        return error;
    }

    public boolean isError() {
        return error != null;
    }



}
