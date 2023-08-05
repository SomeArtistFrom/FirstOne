package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl userDaoJDBC= new UserDaoJDBCImpl();

        userDaoJDBC.dropUsersTable();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.getAllUsers();

        userDaoJDBC.saveUser("Vova","Zhuravlev",(byte)16);
        userDaoJDBC.saveUser("Irina","Vyurova",(byte) 23);
        userDaoJDBC.saveUser("May","Hammer",(byte) 21);
        userDaoJDBC.saveUser("Kolya","Shumaher",(byte) 29);
        System.out.println("----------------");

        userDaoJDBC.removeUserById(3);
        userDaoJDBC.getAllUsers();
        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();
    }
}