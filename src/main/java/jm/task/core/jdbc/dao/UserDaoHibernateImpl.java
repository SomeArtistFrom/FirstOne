package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    public static String createNewTable = "CREATE TABLE IF NOT EXISTS users (\n" +
            " id SERIAL PRIMARY KEY NOT NULL ,\n" +
            " name VARCHAR NOT NULL ,\n" +
            " lastName VARCHAR NOT NULL ,\n" +
            " age SMALLINT NOT NULL );";
    public static String deleteTable = "DROP TABLE IF EXISTS users";
    public static String clearTheTable = "DELETE FROM users";
    public static String selectAllUsers = "SELECT * FROM users";
    private static SessionFactory sessionFactory = Util.getSessionFactory();

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(createNewTable).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(deleteTable).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User c именем - " + name + " добавлен в базу данных");
        } catch (Exception ignored) {
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception ignored) {
        }
    }

    @Override
    public List<User> getAllUsers() {
        List userList;
        try (Session session = sessionFactory.openSession()) {
            userList = session.createSQLQuery(selectAllUsers).addEntity(User.class).list();
            System.out.println(userList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userList;
    }


    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(clearTheTable).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ignored) {
        }
    }
}