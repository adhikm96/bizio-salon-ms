package com.thebizio.biziosalonms.service.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class DBMigrateService {
    public void migrate(DataSource dataSource) {

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .defaultSchema("public")
                .load();

        flyway.migrate();
    }
}
