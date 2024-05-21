package org.thouy.qemu.monitor.client.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import org.thouy.qemu.monitor.client.commands.QemuMonitorCommand;
import org.thouy.qemu.monitor.client.commands.QmpCapabilitiesCommand;
import org.thouy.qemu.monitor.client.model.QemuMonitorGreeting;
import org.thouy.qemu.monitor.client.model.QemuMonitorResponse;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnixDomainSocketAddress;
import java.nio.charset.StandardCharsets;

public class QMPConnection {

    private final String LINE_FEED = "\n";
    //private final Socket socket;
    private final AFUNIXSocket socket = AFUNIXSocket.newInstance();
    private final BufferedReader input;
    private final Writer output;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final QemuMonitorGreeting greeting;

    private boolean isDebug = false;


    /**
     * Constructor for JAVA Socket
     * @param socket
     * @throws IOException
     */
    /*
    public QMPConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);

        invoke(new QemuMonitorCommand<>("qmp_capabilities", null, null));
    }
    */

    /**
     * Constructor for remote socket
     * @param address
     * @param port
     * @throws IOException
     */
    /*
    public QMPConnection(InetAddress address, int port) throws IOException {
        this(new Socket(address, port));
    }
    */


    /**
     * Constructor for UNIX domain socket
     * @param localSocketPath
     * @throws IOException
     */
    public QMPConnection(String localSocketPath) throws IOException {
        /*
        // uppper JDK 16
        UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(localSocketPath);
        Socket socket = new Socket();
        this.socket = socket;
        this.socket.connect(socketAddress);
        */
        File socketFile = new File(localSocketPath);
        socket.connect(AFUNIXSocketAddress.of(socketFile));
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
        this.greeting = read(QemuMonitorGreeting.class);
        invoke(new QmpCapabilitiesCommand());
    }

    /**
     * Invoke and return Object consisted of QMP command execution result
     * @param command
     * @return
     * @param <Result>
     * @param <Response>
     * @throws IOException
     */
    public <Result, Response extends QemuMonitorResponse<Result>> Result call(QemuMonitorCommand<?, Response> command) throws IOException {
        return invoke(command).getResult();
    }


    public void close() throws IOException {
        socket.close();
    }


    /**
     * Execute QMP command
     * @param command
     * @return
     * @param <Response>
     * @throws IOException
     */
    public <Response extends QemuMonitorResponse<?>> Response invoke(QemuMonitorCommand<?, Response> command) throws IOException {
        String commandStr = objectMapper.writeValueAsString(command);
        if (isDebug)
            System.out.println("input : " + commandStr);
        output.write(commandStr);
        output.write(LINE_FEED);
        output.flush();
        Class<Response> returnType = command.getResponseType();
        return read(returnType);
    }


    /**
     * Parse QMP command result via socket's input stream
     * @param type
     * @return
     * @param <T>
     * @throws IOException
     */
    private <T>T read(Class<T> type) throws IOException {
        for(;;) {
            String line = input.readLine();
            if (line == null)
                throw new EOFException();
            if (isDebug)
                System.out.println("output : " + line);
            JsonNode node = objectMapper.readTree(line);
            if (node.get("event") != null)
                continue;
            return objectMapper.treeToValue(node, type);
        }
    }



}
