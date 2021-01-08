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
        Connection connection = Util.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_USER)) {
            connection.setAutoCommit(false);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException cause) {
            RuntimeException createTableError = new RuntimeException("Create table error", cause);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                createTableError.addSuppressed(ex);
            }
            throw createTableError;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignore) {}
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = Util.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_USER)) {
            connection.setAutoCommit(false);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException cause) {
            RuntimeException dropTableError = new RuntimeException("Drop table error", cause);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                dropTableError.addSuppressed(ex);
            }
            throw dropTableError;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignore) {}
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException cause) {
            RuntimeException saveUserError = new RuntimeException("Save user error", cause);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                saveUserError.addSuppressed(ex);
            }
            throw saveUserError;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignore) {}
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_BY_ID)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException cause) {
            RuntimeException removeUserByIdError = new RuntimeException("Remove user by id error", cause);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                removeUserByIdError.addSuppressed(ex);
            }
            throw removeUserByIdError;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignore) {}
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USER)) {
            connection.setReadOnly(true);
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
        Connection connection = Util.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_TABLE_USER)) {
            connection.setAutoCommit(false);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException cause) {
            RuntimeException cleanTableError = new RuntimeException("Clean table error", cause);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                cleanTableError.addSuppressed(ex);
            }
            throw cleanTableError;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignore) {}
            }
        }
    }
}
