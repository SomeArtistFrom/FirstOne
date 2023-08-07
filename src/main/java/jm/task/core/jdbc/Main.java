package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl userDaoJDBC = new UserDaoHibernateImpl();

        userDaoJDBC.dropUsersTable();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.getAllUsers();
        System.out.println("----------------");

        userDaoJDBC.saveUser("Vova", "Zhuravlev", (byte) 16);
        userDaoJDBC.saveUser("Irina", "Vyurova", (byte) 23);
        userDaoJDBC.saveUser("May", "Hammer", (byte) 21);
        userDaoJDBC.saveUser("Kolya", "Shumaher", (byte) 29);
        System.out.println("----------------");

        userDaoJDBC.removeUserById(3);
        userDaoJDBC.getAllUsers();
        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();
    }
}