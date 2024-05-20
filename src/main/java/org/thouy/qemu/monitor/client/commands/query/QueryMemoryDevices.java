package org.thouy.qemu.monitor.client.commands.query;

import org.thouy.qemu.monitor.client.commands.QemuMonitorCommand;
import org.thouy.qemu.monitor.client.model.MemoryDevices;
import org.thouy.qemu.monitor.client.model.QemuMonitorResponse;

import java.util.List;

public class QueryMemoryDevices extends QemuMonitorCommand<QueryMemoryDevices.Arguments, QueryMemoryDevices.Response> {

    public static class Arguments {}

    public static class Response extends QemuMonitorResponse<List<MemoryDevices>> {}

    public QueryMemoryDevices() {
        super("query-memory-devices", null, QueryMemoryDevices.Response.class);
    }
}
