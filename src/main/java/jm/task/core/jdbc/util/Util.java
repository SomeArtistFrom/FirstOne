package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
        public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
        public static final String USERNAME = "postgres";
        public static final String PASSWORD = "tss!thisisecret";
        private static Connection connection;

        private Util() {
        }

        public static Connection getConnection() throws SQLException {
            if (connection == null) {
                synchronized (Util.class) {
                    if (connection == null) {
                        try {
                            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
                            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                        } catch (Exception e) {
                            throw new RuntimeException("Ошибка при подключении к базе данных", e);
                        }
                    }
                }
            }
            return connection;
        }
    }