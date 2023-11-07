package com.thebizio.biziosalonms.service.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class DBMigrateService {

    final Flyway flyway;

    public DBMigrateService(Flyway flyway) {
        this.flyway = flyway;
    }

    public void migrate(DataSource dataSource) {
        Flyway fly = Flyway.configure()
                .configuration(flyway.getConfiguration())
                .dataSource(dataSource)
                .defaultSchema("public")
                .load();
    }
}
