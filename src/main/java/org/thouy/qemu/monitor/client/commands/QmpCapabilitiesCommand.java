package org.thouy.qemu.monitor.client.commands;

import org.thouy.qemu.monitor.client.model.QemuMonitorResponse;

public class QmpCapabilitiesCommand extends QemuMonitorCommand<Void, QmpCapabilitiesCommand.Response> {

    public static class Response extends QemuMonitorResponse<Void> {

    }

    public QmpCapabilitiesCommand() {
        super("qmp_capabilities", null, Response.class);
    }
}
