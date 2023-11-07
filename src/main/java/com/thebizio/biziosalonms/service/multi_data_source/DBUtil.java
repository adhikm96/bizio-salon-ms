package com.thebizio.biziosalonms.service.multi_data_source;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class DBUtil {
    public DataSource getDS(String url, String username, String password) {
        return DataSourceBuilder.create().url(url).username(username).password(password).build();
    }
}
