package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public User users = new User();
    public static String createNewTable = "CREATE TABLE IF NOT EXISTS users (\n" +
            " id SERIAL PRIMARY KEY NOT NULL ,\n" +
            " name VARCHAR NOT NULL ,\n" +
            " lastName VARCHAR NOT NULL ,\n" +
            " age SMALLINT NOT NULL );";
    public static String deleteTable = "DROP TABLE IF EXISTS users";
    public static String deleteUserById = "DELETE FROM users WHERE id = ?";
    public static String addNewUser = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
    public static String clearTheTable = "DELETE FROM users";
    public static String selectAllUsers = "SELECT * FROM users";
    public static String isTableExists = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'users')";
    public static String isUsersExists = "SELECT EXISTS(SELECT 1 FROM users)";

    private static Connection connection = Util.getConnection();

//    static {
//        try {
//            connection = Util.getConnection();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(createNewTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(deleteTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement prStatement = connection.prepareStatement(addNewUser)) {
            prStatement.setString(1, name);
            prStatement.setString(2, lastName);
            prStatement.setByte(3, age);
            prStatement.executeUpdate();

            System.out.println("User c именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement prStatement = connection.prepareStatement(deleteUserById)) {
            if (users.getName() != "null") {
                prStatement.setLong(1, id);
                prStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> listOfUsers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectAllUsers);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                listOfUsers.add(user);

                System.out.println(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfUsers;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(isTableExists);
            resultSet.next();
            boolean tableExists = resultSet.getBoolean(1);

            ResultSet resultSet2 = statement.executeQuery(isUsersExists);
            resultSet2.next();
            boolean userExists = resultSet2.getBoolean(1);

            if ((tableExists) & (userExists)) {
                statement.executeUpdate(clearTheTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}