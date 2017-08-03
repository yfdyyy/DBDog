package com.yfdyyy.dbdog.rule.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.yfdyyy.dbdog.common.utils.DBDogLoggerFactory;
import com.yfdyyy.dbdog.common.utils.StringUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Julie on 2017/8/3.
 */
public class SliceManager {

    private Logger LOGGER = DBDogLoggerFactory.getDbdogLog();

    public static final String DB_NAME = "db_name";

    public static final String IP = "ip";

    public static final String PHYSICAL_NAME = "physical_name";

    public static final String PORT = "port";

    public static final String SLICE_NAME = "slice_name";

    public static final String READER = "reader";

    public static final String WRITER = "writer";

    private static Map<String, String> poolConfigure = new HashMap<String, String>();

    private static Map<String, String> jdbcConfigure = new HashMap<String, String>();

    private static Map<String, PhysicalDB> physicalDBMap = new HashMap<String, PhysicalDB>();

    private static List<Slice> sliceList = new ArrayList<Slice>();

    public List<PhysicalDB> parsePhysicalConfigure(String configure) {
        List<PhysicalDB> physicalDBList = new ArrayList<PhysicalDB>();
        if (StringUtils.isNotBlank(configure)) {
            JSONArray jsonArray = JSON.parseArray(configure);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String dbName = jsonObject.getString(DB_NAME);
                    String ip = jsonObject.getString(IP);
                    String physicalName = jsonObject.getString(PHYSICAL_NAME);
                    String port = jsonObject.getString(PORT);

                    if (StringUtils.isBlank(dbName)
                            || StringUtils.isBlank(ip)
                            || StringUtils.isBlank(physicalName)
                            || StringUtils.isBlank(port)) {
                        LOGGER.error("SliceManager.parsePhysicalConfigure configure error");
                    } else {

                    }
                }
            }
        }
        return physicalDBList;
    }

    public static Map<String, String> parseMapConfigure(String configure) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(configure)) {
            map = JSON.parseObject(configure, new TypeReference<HashMap<String, String>>(){});
        }
        return map;
    }

    public List<Slice> parseSliceConfigure(String configure) {
        List<Slice> sliceList = new ArrayList<Slice>();
        if (StringUtils.isNotBlank(configure)) {
            JSONArray jsonArray = JSON.parseArray(configure);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String sliceName = jsonObject.getString(SLICE_NAME);
                    String readerStr = jsonObject.getString(READER);
                    String writer = jsonObject.getString(WRITER);
                    if (StringUtils.isBlank(sliceName)
                            || StringUtils.isBlank(readerStr)
                            || StringUtils.isBlank(writer)) {
                        LOGGER.error("SliceManager.parseSliceConfigure configure error");
                    } else {

                        List<String> reader = JSONArray.parseArray(readerStr, String.class);
                        if (reader == null || reader.size() == 0) {
                            LOGGER.error("SliceManager.parseSliceConfigure slice reader configure error");
                        } else {
                            Slice slice = new Slice();
                            slice.setSliceName(sliceName);
                            slice.setReader(reader);
                            slice.setWriter(writer);
                            sliceList.add(slice);
                        }
                    }
                }
            }
        }
        return sliceList;
    }
    public static class PhysicalDB {
        private String dbName;

        private String ip;

        private String name;

        private String port;

        public String getDbName() {
            return dbName;
        }

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }
    }

    public static class Slice {
        private String sliceName;

        private List<String> reader;

        private String writer;

        public String getSliceName() {
            return sliceName;
        }

        public void setSliceName(String sliceName) {
            this.sliceName = sliceName;
        }

        public List<String> getReader() {
            return reader;
        }

        public void setReader(List<String> reader) {
            this.reader = reader;
        }

        public String getWriter() {
            return writer;
        }

        public void setWriter(String writer) {
            this.writer = writer;
        }
    }
}
