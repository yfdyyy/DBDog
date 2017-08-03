package com.yfdyyy.dbdog.common.utils;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.URL;

/**
 * Created by Julie on 2017/8/2.
 */
public class DBDogLoggerFactory {
    private static Logger DBDOG_LOG;

    static {
        init();
    }

    public static void init() {
        ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
        try {
            Class<? extends ILoggerFactory> classType = loggerFactory.getClass();
            if (classType.getName().equals("ch.qos.logback.classic.LoggerContext")) {
                Class<?> context = Class.forName("ch.qos.logback.core.Context");
                Class<?> joranConfigurator = Class.forName("ch.qos.logback.classic.joran.JoranConfigurator");
                Object joranConfiguratoroObj = joranConfigurator.newInstance();
                Method setContext = joranConfiguratoroObj.getClass().getMethod("setContext", context);
                setContext.invoke(joranConfiguratoroObj, loggerFactory);
                URL url = DBDogLoggerFactory.class.getClassLoader().getResource("dbdog/logback.xml");

                Method doConfigure = joranConfiguratoroObj.getClass().getMethod("doConfigure", URL.class);
                doConfigure.invoke(joranConfiguratoroObj, url);
            }
        } catch (Exception e) {
            //
        }
        DBDOG_LOG = loggerFactory.getLogger("dbdog");
    }

    public static Logger getDbdogLog() {
        return DBDOG_LOG;
    }
}
