package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;


public class Util {
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "tss!thisisecret";
    public static final String ADDRESS = "org.postgresql.Driver";
    private static Connection connection;

    private Util() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(ADDRESS).getDeclaredConstructor().newInstance();
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при подключении к базе данных", e);
            }
        }
        return connection;
    }


    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                configuration.setProperty("hibernate.connection.driver_class", ADDRESS);
                configuration.setProperty("hibernate.connection.url", URL);
                configuration.setProperty("hibernate.connection.username", USERNAME);
                configuration.setProperty("hibernate.connection.password", PASSWORD);
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

                configuration.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
                configuration.setProperty("hibernate.cache.use_second_level_cache", "false");
                configuration.setProperty("hibernate.cache.use_query_cache", "false");

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}