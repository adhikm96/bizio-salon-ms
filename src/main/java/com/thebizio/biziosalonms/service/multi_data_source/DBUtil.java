package com.thebizio.biziosalonms.service.multi_data_source;

import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

public class DBUtil {
    public static DataSource getDataSource(String url, String username, String password) {
        return DataSourceBuilder.create().url(url).username(username).password(password).build();
    }
}
