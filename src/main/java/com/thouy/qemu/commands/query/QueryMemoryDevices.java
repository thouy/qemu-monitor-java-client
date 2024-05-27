package com.thouy.qemu.commands.query;

import com.thouy.qemu.commands.QemuMonitorCommand;
import com.thouy.qemu.model.QemuMonitorResponse;
import com.thouy.qemu.model.MemoryDevices;

import java.util.List;

public class QueryMemoryDevices extends QemuMonitorCommand<QueryMemoryDevices.Arguments, QueryMemoryDevices.Response> {

    public static class Arguments {}

    public static class Response extends QemuMonitorResponse<List<MemoryDevices>> {}

    public QueryMemoryDevices() {
        super("query-memory-devices", null, QueryMemoryDevices.Response.class);
    }
}
