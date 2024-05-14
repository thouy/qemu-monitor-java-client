package org.thouy.qemu.monitor.client.model;

public class QemuMonitorResponse<T> {

    private T _return;

    public T getResult() {
        return _return;
    }


}
