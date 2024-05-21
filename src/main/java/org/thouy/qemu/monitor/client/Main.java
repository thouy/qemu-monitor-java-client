package org.thouy.qemu.monitor.client;

import org.thouy.qemu.monitor.client.commands.DeviceAddCommand;
import org.thouy.qemu.monitor.client.commands.DeviceDelCommand;
import org.thouy.qemu.monitor.client.commands.ObjectAddCommand;
import org.thouy.qemu.monitor.client.commands.query.QueryHotpluggableCpusCommand;
import org.thouy.qemu.monitor.client.commands.query.QueryMemoryDevices;
import org.thouy.qemu.monitor.client.common.QMPConnection;
import org.thouy.qemu.monitor.client.model.HotpluggableCpus;
import org.thouy.qemu.monitor.client.model.MemoryDevices;
import org.thouy.qemu.monitor.client.model.MemoryProperties;
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
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        QMPConnection connection = new QMPConnection(unixDomainSocketPath);

        System.out.println("Welcome to qemu-monitor-java-client CLI. If you need some help, please enter help.\n");

        while (true) {
            System.out.print("\ninput : ");
            String inputString = scanner.nextLine();

            if (inputString.equals("quit")) {
                System.out.println("See you again :)");
                break;
            }


            switch(inputString) {
                case "query-hotpluggable-cpus" :
                    queryHotpluggableVcpu(connection);
                    break;
                case "cpu-add" :
                    growVcpu(connection);
                    break;
                case "query-memory-devices" :
                    queryMemoryDevices(connection);
                    break;
                case "memory-add" :
                    growMemory(connection);
                    break;
                case "help" :
                    System.out.println("[Notice] Command list");
                    System.out.println("    - query-hotpluggable-cpus");
                    System.out.println("    - cpu-add");
                    System.out.println("    - query-memory-devices");
                    System.out.println("    - memory-add");
                    System.out.println("    - help");
                    System.out.println("    - quit");
                    break;
                default :
                    System.out.println("[Notice] Invalid command. Please enter valid command.");
                    break;
            }
        }

        connection.close();
    }


    private static void growVcpu(QMPConnection connection) throws IOException {
        DeviceAddCommand.Arguments arguments = DeviceAddCommand.Arguments.builder()
                .id("plugged-cpu1")
                .driver("host-x86_64-cpu")
                .socketId(1)
                .coreId(0)
                .threadId(0)
                .build();

        DeviceAddCommand.Response result = connection.invoke(new DeviceAddCommand(arguments));
        if (result.isError()) {
            System.out.printf("     Error message : %s\n", result.getError().desc);
        }
    }

    private static void queryHotpluggableVcpu(QMPConnection connection) throws IOException {
        QueryHotpluggableCpusCommand.Response result
                = connection.invoke(new QueryHotpluggableCpusCommand());
        List<HotpluggableCpus> hotpluggableCpusList = result.getResult();
        hotpluggableCpusList.forEach(cpu -> {
            System.out.printf("     Socket ID : %s / type : %s", cpu.getProps().getSocketId(), cpu.getType());
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
        String objectId = "plugged-mem1";
        String deviceId = "dimm1";
        ObjectAddCommand.Arguments arguments = ObjectAddCommand.Arguments.builder()
                .id(objectId)
                .qomType("memory-backend-ram")
                .size(giga)
                .build();
        ObjectAddCommand.Response objectAddResult = connection.invoke(new ObjectAddCommand(arguments));
        if (!objectAddResult.isError()) {
            DeviceAddCommand.Arguments devArguments = DeviceAddCommand.Arguments.builder()
                    .id(deviceId)
                    .memdev(objectId)
                    .driver("pc-dimm")
                    .build();
            DeviceAddCommand.Response deviceAddResult = connection.invoke(new DeviceAddCommand(devArguments));
        }
    }

    private static void shrinkMemory(QMPConnection connection) throws IOException {
    }

    private static void queryMemoryDevices(QMPConnection connection) throws IOException {
        QueryMemoryDevices.Response result = connection.invoke(new QueryMemoryDevices());
        List<MemoryDevices> memoryList = result.getResult();
        memoryList.forEach(memoryRow -> {
            MemoryProperties memory = memoryRow.getData();
            System.out.printf("     Device ID : %s / memdev : %s", memory.getDeviceId(), memory.getMemdev());
            System.out.println();
        });

    }
}