package com.thouy.qemu;

import com.thouy.qemu.commands.DeviceDelCommand;
import com.thouy.qemu.common.QMPConnection;
import com.thouy.qemu.commands.DeviceAddCommand;
import com.thouy.qemu.commands.ObjectAddCommand;
import com.thouy.qemu.commands.ObjectDelCommand;
import com.thouy.qemu.commands.query.QueryHotpluggableCpusCommand;
import com.thouy.qemu.commands.query.QueryMemoryDevices;
import com.thouy.qemu.model.HotpluggableCpus;
import com.thouy.qemu.model.MemoryDevices;
import com.thouy.qemu.model.MemoryProperties;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static long kilo = 1024;
    static long mega = kilo * kilo;  // 1,048,576
    static long giga = mega * kilo;  // 1,073,741,824
    static long tera = giga * kilo;  // 1,099,511,627,776
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

            String[] commandInput = inputString.split("\\s{1}");
            String command = commandInput[0];
            switch(command) {
                case "query-hotpluggable-cpus" :
                    queryHotpluggableVcpu(connection);
                    break;
                case "cpu-add" :
                    if (commandInput.length != 2) {
                        System.out.println("[Notice] Invalid cpu option. Please enter cpuId");
                    }
                    String cpuId = commandInput[1];

                    growVcpu(connection, cpuId);
                    break;
                case "query-memory-devices" :
                    queryMemoryDevices(connection);
                    break;
                case "memory-add" :
                    if (commandInput.length != 3) {
                        System.out.println("[Notice] Invalid memory option. Please enter objectId, deviceId");
                    }
                    String objectId = commandInput[1];
                    String deviceId = commandInput[2];
                    growMemory(connection, objectId, deviceId);
                    break;
                case "help" :
                    System.out.println("[Notice] Command list");
                    System.out.println("    query-hotpluggable-cpus               Query cpu devices");
                    System.out.println("    cpu-add <cpuId>                       Hot-plug cpu device");
                    System.out.println("    query-memory-devices                  Query hot-plugged memory devices");
                    System.out.println("    memory-add <objectId> <deviceId>      Hot-plug memory device");
                    System.out.println("    help                                  Show command list");
                    System.out.println("    quit                                  Quit CLI");
                    break;
                default :
                    System.out.println("[Notice] Invalid command. Please enter valid command.");
                    break;
            }
        }

        connection.close();
    }


    private static void growVcpu(QMPConnection connection, String cpuId) throws IOException {
        //String cpuId = "plugged-cpu1";
        DeviceAddCommand.Arguments arguments = DeviceAddCommand.Arguments.builder()
                .id(cpuId)
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

    private static void growMemory(QMPConnection connection, String objectId, String deviceId) throws IOException {
        //String objectId = "plugged-mem1";
        //String deviceId = "dimm1";
        ObjectAddCommand.Arguments arguments = ObjectAddCommand.Arguments.builder()
                .id(objectId)
                .qomType("memory-backend-ram")
                .size(giga)
                .build();
        ObjectAddCommand.Response objectAddResult = connection.invoke(new ObjectAddCommand(arguments));
        if(objectAddResult.isError()) {
            System.out.printf("     Error message : %s\n", objectAddResult.getError().desc);
        } else {
            DeviceAddCommand.Arguments devArguments = DeviceAddCommand.Arguments.builder()
                    .id(deviceId)
                    .memdev(objectId)
                    .driver("pc-dimm")
                    .build();
            DeviceAddCommand.Response deviceAddResult = connection.invoke(new DeviceAddCommand(devArguments));
            if (deviceAddResult.isError()) {
                System.out.printf("     Error message : %s\n", deviceAddResult.getError().desc);
                ObjectDelCommand.Arguments objDelArguments = ObjectDelCommand.Arguments.builder()
                        .id(objectId)
                        .build();
                ObjectDelCommand.Response objectDelResult = connection.invoke(new ObjectDelCommand(objDelArguments));
                if (objectDelResult.isError())
                    System.out.printf("     Error message : %s\n", objectDelResult.getError().desc);
            }
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