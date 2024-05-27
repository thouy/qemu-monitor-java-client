package com.thouy.qemu.commands;

import com.thouy.qemu.model.QemuMonitorResponse;

public class QmpCapabilitiesCommand extends QemuMonitorCommand<Void, QmpCapabilitiesCommand.Response> {

    public static class Response extends QemuMonitorResponse<Void> {

    }

    public QmpCapabilitiesCommand() {
        super("qmp_capabilities", null, Response.class);
    }
}
