package org.thouy.qemu.monitor.client;

import org.thouy.qemu.monitor.client.commands.DeviceAddCommand;
import org.thouy.qemu.monitor.client.commands.DeviceDelCommand;
import org.thouy.qemu.monitor.client.commands.ObjectAddCommand;
import org.thouy.qemu.monitor.client.commands.query.QueryHotpluggableCpusCommand;
import org.thouy.qemu.monitor.client.common.QMPConnection;
import org.thouy.qemu.monitor.client.model.HotpluggableCpus;
import org.thouy.qemu.monitor.client.model.QemuMonitorResponse;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    /*
    public static void main(String[] args) throws IOException {

        String monitorPath = "/var/run/cloudit/7dce683a-e49b-45ac-a8f4-0a231639a7cc/1hv37zkhmezp1.socket";

        String commandPrefix = "{\"execute\":\"qmp_capabilities\"}";
        String qmpCommand =
                "{\"execute\": \"device_add\", \"arguments\": {\"socket-id\": 1, \"driver\": \"host-x86_64-cpu\", \"id\": \"hotplug-cpu-2\", \"core-id\": 0, \"thread-id\": 0}}";
        String[] command = new String[]{"/bin/sh", "-c",
                "echo '" + commandPrefix + qmpCommand + "' | /usr/bin/nc -U " + monitorPath};

        QEMUMonitorUtils.executeCommand(command);
    }
    */

    static long kilo = 1024;
    static long mega = kilo * kilo;
    static long giga = mega * mega;
    static long tera = giga * giga;
    //static String unixDomainSocketPath = "/tmp/unix-domain.socket";
    static String unixDomainSocketPath = "/var/run/cloudit/7dce683a-e49b-45ac-a8f4-0a231639a7cc/1hv37zkhmezp1.socket";

    /**
     * qemu-monitor-java-client test
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        QMPConnection connection = new QMPConnection(unixDomainSocketPath);
        queryHotpluggableVcpu(connection);
        growVcpu(connection);
        queryHotpluggableVcpu(connection);
        connection.close();
    }

    private static void growVcpu(QMPConnection connection) throws IOException {
        connection.call(new QueryHotpluggableCpusCommand("query-hotplugged-cpu", null));
        DeviceAddCommand.Arguments arguments = DeviceAddCommand.Arguments.builder()
                .id("plugged-cpu1")
                .driver("host-x86_64-cpu")
                .socketId(1)
                .coreId(0)
                .threadId(0)
                .build();

        DeviceAddCommand.Response result = connection.invoke(new DeviceAddCommand(arguments));
        if (result.isError()) {
            System.out.println("error desc : " + result.getError().desc);
        }
    }

    private static void queryHotpluggableVcpu(QMPConnection connection) throws IOException {
        QueryHotpluggableCpusCommand.Response result
                = connection.invoke(new QueryHotpluggableCpusCommand("query-hotplugged-cpu", null));
        List<HotpluggableCpus> hotpluggableCpusList = result.getResult();
        hotpluggableCpusList.forEach(cpu -> {
            System.out.printf("Socket ID : %s / type : %s", cpu.getProps().getSocketId(), cpu.getType());
            if (cpu.getQomPath() != null)
                System.out.printf(" / QOM Path : %s", cpu.getQomPath());
            System.out.println();
        });

    }

    private static void shrinkVcpu(QMPConnection connection) throws IOException{
        DeviceDelCommand.Arguments arguments = DeviceDelCommand.Arguments.builder()
                .id("plugged-cpu1")
                .build();

        connection.call(new DeviceDelCommand(arguments));
    }

    private static void growMemory(QMPConnection connection) throws IOException {
        ObjectAddCommand.Arguments arguments = ObjectAddCommand.Arguments.builder()
                .id("plugged-mem1")
                .qomType("memory-backend-ram")
                .size(giga)
                .build();

        connection.call(new ObjectAddCommand(arguments));
    }

    private static void shrinkMemory(QMPConnection connection) throws IOException {
    }
}