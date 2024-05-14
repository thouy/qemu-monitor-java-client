package org.thouy.qemu.monitor.client;

import org.thouy.qemu.monitor.client.commands.DeviceAddCommand;
import org.thouy.qemu.monitor.client.commands.ObjectAddCommand;
import org.thouy.qemu.monitor.client.common.QMPConnection;

import java.io.IOException;

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

    public static void main(String[] args) throws IOException {

        growVcpu();
    }

    private static void growVcpu() throws IOException {
        String monitorPath = "/var/run/cloudit/7dce683a-e49b-45ac-a8f4-0a231639a7cc/1hv37zkhmezp1.socket";
        QMPConnection connection = new QMPConnection(monitorPath);
        DeviceAddCommand.Arguments arguments = new DeviceAddCommand.Arguments("plugged-cpu1", "host-x86_64-cpu", 1);

        connection.call(new DeviceAddCommand(arguments));
    }

    private static void shrinkVcpu(String id) throws IOException{
        String monitorPath = "/var/run/cloudit/7dce683a-e49b-45ac-a8f4-0a231639a7cc/1hv37zkhmezp1.socket";
        QMPConnection connection = new QMPConnection(monitorPath);
    }

    private static void growMemory() throws IOException {
        String monitorPath = "/var/run/cloudit/7dce683a-e49b-45ac-a8f4-0a231639a7cc/1hv37zkhmezp1.socket";
        QMPConnection connection = new QMPConnection(monitorPath);
        ObjectAddCommand.Arguments arguments = new ObjectAddCommand.Arguments("plugged-mem1", "memory-backend-ram", giga);
        connection.call(new ObjectAddCommand(arguments));
    }

    private static void shrinkMemory(String id) throws IOException {
        String monitorPath = "/var/run/cloudit/7dce683a-e49b-45ac-a8f4-0a231639a7cc/1hv37zkhmezp1.socket";
        QMPConnection connection = new QMPConnection(monitorPath);
    }
}