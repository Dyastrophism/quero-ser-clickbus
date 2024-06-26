package com.clickbus.alpha;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import io.r2dbc.spi.ConnectionFactory;

@TestConfiguration
public class TestConfig {
    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        CompositeDatabasePopulator populate = new CompositeDatabasePopulator();
        populate.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        populate.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("import.sql")));
        initializer.setDatabasePopulator(populate);

        return initializer;
    }
}