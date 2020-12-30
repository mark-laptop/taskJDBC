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

    private static final String CREATE_TABLE_USER =
            "CREATE TABLE users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(255) NOT NULL," +
                    "last_name VARCHAR(255) NOT NULL," +
                    "age SMALLINT NOT NULL)";
    private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS users";
    private static final String CLEAN_TABLE_USER = "DELETE FROM users";
    private static final String SAVE_USER = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
    private static final String REMOVE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    private static final String GET_ALL_USER = "SELECT id, name, last_name, age FROM users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        dropUsersTable();
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_USER)) {
            preparedStatement.execute();
        } catch (SQLException cause) {
            throw new RuntimeException("Create table error", cause);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_USER)) {
            preparedStatement.execute();
        } catch (SQLException cause) {
            throw new RuntimeException("Drop table error", cause);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException cause) {
            throw new RuntimeException("Save user error", cause);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException cause) {
            throw new RuntimeException("Remove user by id error", cause);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USER)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException cause) {
            throw new RuntimeException("Get all users error", cause);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_TABLE_USER)) {
            preparedStatement.execute();
        } catch (SQLException cause) {
            throw new RuntimeException("Clean table error", cause);
        }
    }
}
