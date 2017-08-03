package com.yfdyyy.dbdog.rule.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yfdyyy.dbdog.common.utils.DBDogLoggerFactory;
import com.yfdyyy.dbdog.common.utils.StringUtils;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by Julie on 2017/8/3.
 */
public class ConfigureManager {

    private static final Logger LOGGER = DBDogLoggerFactory.getDbdogLog();

    public static final String NAME = "name";

    public static final String BACKEND = "backend";

    public static final String RESOURCE = "resource";

    public static final String ACCOUNT = "account";

    public static final String POOL = "pool";

    public static final String JDBC = "jdbc";

    public static final String PHYSICAL_DB = "physical_db";

    public static final String SLICE = "slice";

    public static void parseConfigure(String configure) {
        if (StringUtils.isBlank(configure)) {
            return;
        }
        JSONObject jsonObject = JSON.parseObject(configure);
        if (jsonObject == null) {
            return;
        }
        String name = jsonObject.getString(NAME);

        String backendStr = jsonObject.getString(BACKEND);

        JSONObject backendObj = JSON.parseObject(backendStr);

        if (backendObj == null) {
            LOGGER.error("");
        }
        String accountStr = backendObj.getString(ACCOUNT);
        List<AccountManager.Account> accountList = AccountManager.parseConfigure(accountStr);

        if (accountList == null || accountList.size() == 0) {
            LOGGER.error("");
            return;
        }

        String resourceStr = backendObj.getString(RESOURCE);
        if (StringUtils.isBlank(resourceStr)) {
            LOGGER.error("");
            return;
        }
        JSONObject resourceObj = JSON.parseObject(resourceStr);

        String poolString = resourceObj.getString(POOL);
        String jdbcString = resourceObj.getString(JDBC);

        Map<String, String> poolConfigure = SliceManager.parseMapConfigure(poolString);
        Map<String, String> jdbcConfigure = SliceManager.parseMapConfigure(jdbcString);


    }
}
