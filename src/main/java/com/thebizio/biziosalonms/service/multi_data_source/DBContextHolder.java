package com.thebizio.biziosalonms.service.multi_data_source;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setCurrentDb(String dbType) {
        log.info("setting current db : " + dbType);
        contextHolder.set(dbType);
    }

    public static String getCurrentDb() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}
