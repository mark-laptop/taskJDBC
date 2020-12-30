package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl(new UserDaoJDBCImpl());
        userService.createUsersTable();
        for (int i = 0; i < 4; i++) {
            String name = "Name " + i;
            String lastName = "Last name " + i;
            byte age = (byte) (i + 1);
            userService.saveUser(name, lastName, age);
            System.out.printf("User с именем - %s добавлен в базу данных%n", name);
        }

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
