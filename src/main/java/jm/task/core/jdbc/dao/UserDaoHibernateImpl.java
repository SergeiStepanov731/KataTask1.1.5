package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static jm.task.core.jdbc.util.Util.getSessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {


    private static final SessionFactory factory = getSessionFactory();

    public UserDaoHibernateImpl() {}

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS  Users (id SERIAL PRIMARY KEY, name VARCHAR(20),lastname VARCHAR(20),age INT)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS Users";



    @Override
    public void createUsersTable() {

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        }

    }


    @Override
    public void dropUsersTable() {

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(DROP_TABLE).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction transaction = null;
        try (Session session = factory.getCurrentSession()) {
            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("Пользователь " + name + " добавлен в базу данных.");

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();

        }
    }


    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;
        try (Session session = factory.getCurrentSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            System.out.println("Пользователь с id " + id + " успешно удален" + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();

        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> list = new ArrayList<>();
        try (Session session = factory.getCurrentSession()) {
            transaction = session.beginTransaction();
            list = session.createQuery("from User", User.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        }
        return list;
    }


    @Override
    public void cleanUsersTable() {

        Transaction transaction = null;
        try (Session session = factory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("truncate table users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();

        }
    }
}

