package jm.task.core.jdbc.util;


import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/users?serverTimezone=Europe/Moscow";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Util() {}

    public static Connection getConnection() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(URL);
        dataSource.setUser(USERNAME);
        dataSource.setPassword(PASSWORD);
        Connection connection;
        try {
            connection = dataSource.getConnection();
        }catch (SQLException cause) {
            throw new RuntimeException("", cause);
        }
        return connection;
    }
}
