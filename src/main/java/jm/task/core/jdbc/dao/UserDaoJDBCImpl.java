package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS  Users (id SERIAL PRIMARY KEY, name VARCHAR(20),lastname VARCHAR(20),age INT)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS Users";
    private static final String SAVE_USER = "INSERT INTO Users(name,lastname,age) VALUES (?,?,?)";
    private static final String DELETE_BY_ID = "DELETE FROM Users WHERE id = ?";
    private static final String GET_USERS = "SELECT * FROM Users";
    private static final String CLEAN_TABLE = "TRUNCATE TABLE Users";

    public void createUsersTable() {

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void dropUsersTable() {

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveUser(String name, String lastName, byte age) {

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            System.out.println("Пользователь " + name + " добавлен в базу данных.");
        }

    }


    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            System.out.println("Пользователь с id " + id + " успешно удален" + "\n");
        }
    }

    public List<User> getAllUsers() {

        List <User> list = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS)) {
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                User user=new User();
                user.setId(res.getLong("id"));
                user.setName(res.getString("name"));
                user.setLastName(res.getString("lastName"));
                user.setAge(res.getByte("age"));
                list.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }



    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Таблица была очищена");
        }

    }
}
