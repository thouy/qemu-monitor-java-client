package org.thouy.qemu.monitor.client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class QEMUMonitorUtils {

    public static void executeCommand(String[] commandString) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(commandString);

        InputStream input = process.getInputStream();
        InputStreamReader isReader = new InputStreamReader(input, StandardCharsets.UTF_8);
        BufferedReader bufReader = new BufferedReader(isReader);

        String line;
        StringBuffer strBuffer = new StringBuffer();
        while ((line = bufReader.readLine()) != null) {
            strBuffer.append(line);
            strBuffer.append("\n");
        }
        System.out.println(strBuffer.toString());



    }

    private JSONObject parseQMPResult(String resultJson) throws ParseException {
        return (JSONObject) new JSONParser().parse(resultJson);
    }


}
