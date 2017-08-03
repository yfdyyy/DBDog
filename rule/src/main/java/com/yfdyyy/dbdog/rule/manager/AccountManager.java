package com.yfdyyy.dbdog.rule.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yfdyyy.dbdog.common.utils.DBDogLoggerFactory;
import com.yfdyyy.dbdog.common.utils.StringUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julie on 2017/8/2.
 */
public class AccountManager {

    private static final Logger LOGGER = DBDogLoggerFactory.getDbdogLog();

    public static final String DB_USER = "db_user";

    public static final String DB_PASSWORD = "db_password";

    public static final String PHYSICAL_USER = "physical_user";

    public static final String PHYSICAL_PASSWORD = "physical_password";

    private static List<Account> accountList;

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public static List<Account> parseConfigure(String configure) {
        List<Account> newAccountList = null;
        if (StringUtils.isNotBlank(configure)) {
            JSONArray jsonArray = JSON.parseArray(configure);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject != null) {
                        String dbUser = jsonObject.getString(DB_USER);
                        String dbPassword = jsonObject.getString(DB_PASSWORD);
                        String physicalUser = jsonObject.getString(PHYSICAL_USER);
                        String physicalPassword = jsonObject.getString(PHYSICAL_PASSWORD);

                        if (StringUtils.isBlank(dbUser)
                                || StringUtils.isBlank(dbPassword)
                                || StringUtils.isBlank(physicalPassword)
                                || StringUtils.isBlank(physicalUser)) {
                            LOGGER.error("AccountManager.parseConfigure error account configure null");
                        } else {
                            Account account = new Account();
                            account.setDbUser(dbUser);
                            account.setDbPassword(dbPassword);
                            account.setPhysicalUser(physicalUser);
                            account.setPhysicalPassword(physicalPassword);
                            if (newAccountList == null) {
                                newAccountList = new ArrayList<Account>();
                            }
                            newAccountList.add(account);
                        }
                    }
                }
            }
        }
        return newAccountList;

    }

    public static class Account {
        private String dbUser;
        private String dbPassword;

        private String physicalUser;
        private String physicalPassword;

        public String getDbUser() {
            return dbUser;
        }

        public void setDbUser(String dbUser) {
            this.dbUser = dbUser;
        }

        public String getDbPassword() {
            return dbPassword;
        }

        public void setDbPassword(String dbPassword) {
            this.dbPassword = dbPassword;
        }

        public String getPhysicalUser() {
            return physicalUser;
        }

        public void setPhysicalUser(String physicalUser) {
            this.physicalUser = physicalUser;
        }

        public String getPhysicalPassword() {
            return physicalPassword;
        }

        public void setPhysicalPassword(String physicalPassword) {
            this.physicalPassword = physicalPassword;
        }
    }
}
