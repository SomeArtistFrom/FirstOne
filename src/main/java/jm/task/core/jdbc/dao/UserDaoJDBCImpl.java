package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public User usersD = new User();
    public static String createNewTable = "CREATE TABLE users (\n" +
            " id SERIAL PRIMARY KEY NOT NULL ,\n" +
            " name VARCHAR NOT NULL ,\n" +
            " lastName VARCHAR NOT NULL ,\n" +
            " age SMALLINT NOT NULL );";
    public static String isTableExists = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'users')";
    public static String deleteTable = "DROP TABLE users";
    public static String isUsersExists = "SELECT EXISTS(SELECT 1 FROM users)";
    public static String deleteUserById = "DELETE FROM users WHERE id = ?";
    public static String addNewUser = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
    public static String clearTheTable = "DELETE FROM users";
    public static String selectAllUsers = "SELECT * FROM users";

    public void createUsersTable() {
        try (Connection connection = Util.needConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(isTableExists);
            resultSet.next();
            boolean tableExists = resultSet.getBoolean(1);

            if (tableExists) {
                System.out.println("Уппс, дублирование таблицы!");
            } else {
                statement.execute(createNewTable);
                System.out.println("Таблица успешно создана!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.needConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(isTableExists);
            resultSet.next();
            boolean tableExists = resultSet.getBoolean(1);

            if (tableExists) {
                statement.execute(deleteTable);
                System.out.println("Таблица успешно удалена!");
            } else {
                System.out.println("Таблицы не было, нечего удалять!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (Connection connection = Util.needConnection();
             Statement statement = connection.createStatement();
             PreparedStatement prStatement = connection.prepareStatement(addNewUser)) {

            ResultSet resultSet = statement.executeQuery(isTableExists);
            resultSet.next();
            boolean tableExists = resultSet.getBoolean(1);

            if ((tableExists)) {
                prStatement.setString(1, name);
                prStatement.setString(2, lastName);
                prStatement.setByte(3, age);
                prStatement.executeUpdate();

                System.out.println("User c именем - " + name + " добавлен в базу данных");
            } else {
                System.out.println("Мы пытаемся добавить user в несуществующую таблицу");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {

        try (Connection connection = Util.needConnection();
             Statement statement = connection.createStatement();
             PreparedStatement prStatement = connection.prepareStatement(deleteUserById)) {

            ResultSet resultSet = statement.executeQuery(isUsersExists);
            resultSet.next();
            boolean userExists = resultSet.getBoolean(1);
            if (userExists) {
                if (usersD.getName() != "null") {
                    prStatement.setLong(1, id);
                    prStatement.executeUpdate();
                }
                System.out.println("Пользователь успешно удален по id");
            } else {
                System.out.println("Таблица пуста, удаление пользователя по id невозможно");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> listOfUsers = new ArrayList<>();
        try (Connection connection = Util.needConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet2 = statement.executeQuery(isTableExists);
            resultSet2.next();
            boolean tableExists = resultSet2.getBoolean(1);

            ResultSet resultSet3 = statement.executeQuery(isUsersExists);
            resultSet3.next();
            boolean userExists = resultSet3.getBoolean(1);

            ResultSet resultSet = statement.executeQuery(selectAllUsers);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);

                listOfUsers.add(user);
                System.out.println(user);
            }
            if (!userExists) {
                System.out.println("Таблица есть, но список равен нулю");
            } else if (!tableExists) {
                System.out.println("Таблицы не существует, не можем получить список пользователей!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfUsers;
    }

    public void cleanUsersTable() {

        try (Connection connection = Util.needConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(isTableExists);
            resultSet.next();
            boolean tableExists = resultSet.getBoolean(1);

            ResultSet resultSet2 = statement.executeQuery(isUsersExists);
            resultSet2.next();
            boolean userExists = resultSet2.getBoolean(1);

            if (tableExists) {
                if (userExists) {
                    statement.executeUpdate(clearTheTable);
                    System.out.println("Таблица успешно очищена");
                } else {
                    System.out.println("Таблица уже пуста");
                }
            } else {
                System.out.println("Таблицы не существует");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}