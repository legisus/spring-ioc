package com.codesoft.edu;

import com.codesoft.edu.model.User;
import com.codesoft.edu.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RunWith(JUnitPlatform.class)
public class UserServiceTest {
    private static UserService userService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        annotationConfigContext.close();
    }

    @Test
    public void checkAddUser() {
        User user = new User();
        user.setEmail("newuser@example.com");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setPassword("password");

        User expected = new User();
        expected.setEmail("newuser@example.com");
        expected.setFirstName("FirstName");
        expected.setLastName("LastName");
        expected.setPassword("password");

        User actual = userService.addUser(user);
        Assertions.assertNotNull(actual, "User should be added successfully");
        Assertions.assertEquals(expected, actual, "check message");
    }

    @Test
    public void shouldNotAddDuplicateEmailUser() {
        User user1 = new User();
        user1.setEmail("duplicate@example.com");
        user1.setFirstName("First");
        user1.setLastName("Duplicate");
        user1.setPassword("password1");
        userService.addUser(user1);

        User user2 = new User();
        user2.setEmail("duplicate@example.com");
        user2.setFirstName("Second");
        user2.setLastName("Duplicate");
        user2.setPassword("password2");

        User actual = userService.addUser(user2);
        Assertions.assertNull(actual, "Duplicate email user should not be added");
    }

    @Test
    public void checkUpdateUser() {
        User user = new User();
        user.setEmail("newuser@example.com");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setPassword("password");
        userService.addUser(user);

        User updatedUser = new User();
        updatedUser.setEmail("apdateduser@example.com");
        updatedUser.setFirstName("UpdatedFirstName");
        updatedUser.setLastName("UpdatedLastName");
        updatedUser.setPassword("UpdatedPassword");
        userService.updateUser(updatedUser);

        User expected = new User();
        expected.setEmail("apdateduser@example.com");
        expected.setFirstName("UpdatedFirstName");
        expected.setLastName("UpdatedLastName");
        expected.setPassword("UpdatedPassword");
        userService.addUser(expected);

        Assertions.assertEquals(expected, updatedUser, "User should be updated successfully");
    }

    @Test
    public void checkDeleteUser() {
        User user = new User();
        user.setEmail("MyUser");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setPassword("password");
        userService.addUser(user);

        userService.deleteUser(user);

        Assertions.assertTrue(userService.getAll().isEmpty(), "User should be deleted successfully");
    }

    @Test
    public void checkGetAll() {
        long countOfUsers = userService.getAll().size();

        User user1 = new User();
        user1.setEmail("first@umail.com");
        user1.setFirstName("FirstName1");
        user1.setLastName("LastName1");
        user1.setPassword("password1");
        userService.addUser(user1);

        User user2 = new User();
        user2.setEmail("second@umail.com");
        user2.setFirstName("FirstName2");
        user2.setLastName("LastName2");
        user2.setPassword("password2");
        userService.addUser(user2);

        User user3 = new User();
        user3.setEmail("tird@umail.com");
        user3.setFirstName("FirstName3");
        user3.setLastName("LastName3");
        user3.setPassword("password3");
        userService.addUser(user3);

        Assertions.assertEquals(countOfUsers + 3, userService.getAll().size(), "All users should be returned");
    }

}
