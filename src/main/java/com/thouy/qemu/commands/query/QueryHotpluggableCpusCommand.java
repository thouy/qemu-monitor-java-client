package com.thouy.qemu.commands.query;

import com.thouy.qemu.commands.QemuMonitorCommand;
import com.thouy.qemu.model.HotpluggableCpus;
import com.thouy.qemu.model.QemuMonitorResponse;

import java.util.List;

public class QueryHotpluggableCpusCommand extends QemuMonitorCommand<QueryHotpluggableCpusCommand.Arguments, QueryHotpluggableCpusCommand.Response> {

    public static class Arguments {}

    /**
     *  e.g.
     *      {
     *          "return": [
     *              {
     *                  "props": {
     *                      "core-id": 0,
     *                      "thread-id": 0,
     *                      "node-id": 0,
     *                      "socket-id": 7
     *                  },
     *                  "vcpus-count": 1,
     *                  "type": "host-x86_64-cpu"
     *              },
     *              {
     *                  "props": {
     *                      "core-id": 0,
     *                      "thread-id": 0,
     *                      "node-id": 0,
     *                      "socket-id": 6
     *                  },
     *                  "vcpus-count": 1,
     *                  "type": "host-x86_64-cpu"
     *              }, ...
     *          ]
     *      }
     */
    public static class Response extends QemuMonitorResponse<List<HotpluggableCpus>> {}

    public QueryHotpluggableCpusCommand() {
        super("query-hotpluggable-cpus", null, Response.class);
    }

}