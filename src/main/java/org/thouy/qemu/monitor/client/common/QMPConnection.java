package org.thouy.qemu.monitor.client.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.thouy.qemu.monitor.client.model.QemuMonitorCommand;
import org.thouy.qemu.monitor.client.model.QemuMonitorResponse;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnixDomainSocketAddress;
import java.nio.charset.StandardCharsets;

public class QMPConnection {

    private Socket socket;
    private BufferedReader input;
    private Writer output;

    private final String LINE_FEED = "/n";
    private final ObjectMapper objectMapper = new ObjectMapper();


    public QMPConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);

        invoke(new QemuMonitorCommand<>("qmp_capabilities", QemuMonitorResponse.class, null));
    }

    /**
     * Constructor for remote socket
     * @param address
     * @param port
     * @throws IOException
     */
    public QMPConnection(InetAddress address, int port) throws IOException {
        this(new Socket(address, port));
    }

    /**
     * Constructor for UNIX domain socket
     * @param localSocketPath
     * @throws IOException
     */
    public QMPConnection(String localSocketPath) throws IOException {
        UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(localSocketPath);
        Socket socket = new Socket();
        socket.connect(socketAddress);

        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);

        invoke(new QemuMonitorCommand<>("qmp_capabilities", QemuMonitorResponse.class, null));
    }


    private <T>T read(Class<T> type) throws IOException {
        for(;;) {
            String line = input.readLine();
            if (line == null)
                throw new EOFException();
            JsonNode node = objectMapper.readTree(line);
            if (node.get("event") != null)
                continue;
            return objectMapper.treeToValue(node, type);
        }
    }

    public <Response extends QemuMonitorResponse<?>> Response invoke(QemuMonitorCommand<?, Response> command) throws IOException {
        String commandStr = objectMapper.writeValueAsString(command);
        output.write(commandStr + LINE_FEED);
        output.flush();
        Class<Response> returnType = command.getResponseType();
        return read(returnType);
    }

    public <Result, Response extends QemuMonitorResponse<Result>> Result call(QemuMonitorCommand<?, Response> command) throws IOException {
        return invoke(command).getResult();
    }
}
