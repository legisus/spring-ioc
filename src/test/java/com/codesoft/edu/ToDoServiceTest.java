package com.codesoft.edu;

import com.codesoft.edu.model.ToDo;
import com.codesoft.edu.model.User;
import com.codesoft.edu.service.ToDoService;
import com.codesoft.edu.service.UserService;
import com.codesoft.edu.service.impl.ToDoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnitPlatform.class)
public class ToDoServiceTest {
    private static UserService userService;
    private static ToDoService toDoService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        toDoService = annotationConfigContext.getBean(ToDoServiceImpl.class);
        annotationConfigContext.close();
    }

    @Test
    public void checkAddToDo() {
        User user1 = new User("Dima", "Ivanenko", "dima@gmail.com", "pass");
        User user2 = new User("Oleg", "Ivanen", "oleg@gmail.com", "pass");
        User user3 = new User("Olya", "Ivanen", "olya@gmail.com", "pass");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        ToDo toDo1 = new ToDo("todo1", LocalDateTime.of(2024, 12, 12, 15, 15), user1);
        ToDo toDo2 = new ToDo("todo2", LocalDateTime.of(2024, 12, 12, 15, 15), user2);
        ToDo toDo3 = new ToDo("todo3", LocalDateTime.of(2024, 12, 12, 15, 15), user3);


        toDoService.addTodo(toDo1, user1);
        toDoService.addTodo(toDo2, user1);
        toDoService.addTodo(toDo2, user2);
        toDoService.addTodo(toDo3, user3);

        List<ToDo> expected = new ArrayList<>();
        expected.add(toDo1);
        expected.add(toDo2);
        expected.add(toDo2);
        expected.add(toDo3);

        System.out.println("expected " + " \n" + expected);
        List<ToDo> actual = toDoService.getAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkAddToDoWithDuplicateUser() {

//        User user1 = new User("Dima", "Ivanenko", "dima@gmail.com", "pass");
        User user2 = new User("Dima", "Ivanenko", "dima@gmail.com", "pass");
        User user3 = new User("Olya", "Ivanen", "olya@gmail.com", "pass");
//        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

//        ToDo toDo1 = new ToDo("todo1", LocalDateTime.of(2024, 12, 12, 15, 15), user1);
        ToDo toDo2 = new ToDo("todo2", LocalDateTime.of(2024, 12, 12, 15, 15), user2);
        ToDo toDo3 = new ToDo("todo3", LocalDateTime.of(2024, 12, 12, 15, 15), user3);

        List<ToDo> expected = new ArrayList<>();
//        expected.add(toDo1);
        expected.add(toDo2);
        expected.add(toDo3); //added


//        toDoService.addTodo(toDo1, user1);
        toDoService.addTodo(toDo2, user2);
        toDoService.addTodo(toDo3, user3);

        List<ToDo> actual = toDoService.getAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateTodo() {
        User user1 = new User("Dima", "Ivanenko", "dima@gmail.com", "pass");
        User user2 = new User("Oleg", "Ivanen", "oleg@gmail.com", "pass");
        User user3 = new User("Olya", "Ivanen", "olya@gmail.com", "pass");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        ToDo toDo1 = new ToDo("todo1", LocalDateTime.of(2024, 12, 12, 15, 15), user1);
        ToDo toDo2 = new ToDo("todo2", LocalDateTime.of(2024, 12, 12, 15, 15), user2);
        ToDo toDo3 = new ToDo("todo3", LocalDateTime.of(2024, 12, 12, 15, 15), user3);

        toDoService.addTodo(toDo1, user1);
        toDoService.addTodo(toDo2, user2);
        toDoService.addTodo(toDo3, user3);

        ToDo updateTodo = new ToDo("todo4", LocalDateTime.of(2024, 12, 12, 15, 15), user3);

        List<ToDo> expected = new ArrayList<>();
        expected.add(toDo1);
        expected.add(toDo2);
        expected.add(updateTodo);

        toDoService.updateTodo(updateTodo);
        List<ToDo> actual = toDoService.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetTodoByUser() {
        User user1 = new User("Dima", "Ivanenko", "dima@gmail.com", "pass");
        User user2 = new User("Oleg", "Ivanen", "oleg@gmail.com", "pass");
        User user3 = new User("Olya", "Ivanen", "olya@gmail.com", "pass");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        ToDo toDo1 = new ToDo("todo1", LocalDateTime.of(2024, 12, 12, 15, 15), user1);
        ToDo toDo2 = new ToDo("todo2", LocalDateTime.of(2024, 12, 12, 15, 15), user2);
        ToDo toDo3 = new ToDo("todo3", LocalDateTime.of(2024, 12, 12, 15, 15), user3);

        toDoService.addTodo(toDo1, user1);
        toDoService.addTodo(toDo2, user1);
        toDoService.addTodo(toDo2, user2);
        toDoService.addTodo(toDo3, user3);
        List<ToDo> expected = new ArrayList<>();
        expected.add(toDo1);
        expected.add(toDo2);
        List<ToDo> actual = toDoService.getByUser(user1);
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void testGetAllTodo() {
        User user1 = new User("Dima", "Ivanenko", "dima@gmail.com", "pass");
        User user2 = new User("Oleg", "Ivanen", "oleg@gmail.com", "pass");
        User user3 = new User("Olya", "Ivanen", "olya@gmail.com", "pass");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        ToDo toDo1 = new ToDo("todo1", LocalDateTime.of(2024, 12, 12, 15, 15), user1);
        ToDo toDo2 = new ToDo("todo2", LocalDateTime.of(2024, 12, 12, 15, 15), user2);
        ToDo toDo3 = new ToDo("todo3", LocalDateTime.of(2024, 12, 12, 15, 15), user3);

        toDoService.addTodo(toDo1, user1);
        toDoService.addTodo(toDo2, user2);
        toDoService.addTodo(toDo1, user2);
        toDoService.addTodo(toDo3, user3);
        toDoService.addTodo(toDo2, user3);
        List<ToDo> expected = new ArrayList<>();
        expected.add(toDo1);
        expected.add(toDo2);
        expected.add(toDo1);
        expected.add(toDo3);
        expected.add(toDo2);
        List<ToDo> actual = toDoService.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDeleteTodo() {
        User user1 = new User("Dima", "Ivanenko", "dima@gmail.com", "pass");
        User user2 = new User("Oleg", "Ivanen", "oleg@gmail.com", "pass");
        User user3 = new User("Olya", "Ivanen", "olya@gmail.com", "pass");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        ToDo toDo1 = new ToDo("todo1", LocalDateTime.of(2024, 12, 12, 15, 15), user1);
        ToDo toDo2 = new ToDo("todo2", LocalDateTime.of(2024, 12, 12, 15, 15), user2);
        ToDo toDo3 = new ToDo("todo3", LocalDateTime.of(2024, 12, 12, 15, 15), user3);

        toDoService.addTodo(toDo1, user1);
        toDoService.addTodo(toDo2, user1);
        toDoService.addTodo(toDo1, user2);
        toDoService.addTodo(toDo3, user3);
        toDoService.addTodo(toDo2, user3);
        System.out.println(toDoService.getAll());

        List<ToDo> expected = new ArrayList<>();

        expected.add(toDo1);
        expected.add(toDo1);
        expected.add(toDo3);
        expected.add(toDo2);
        System.out.println("exp " + expected);
        toDoService.deleteTodo(toDo2);
        List<ToDo> actual = toDoService.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getByUserTitle() {
        User user1 = new User("Dima", "Ivanenko", "dima@gmail.com", "pass");
        User user2 = new User("Oleg", "Ivanen", "oleg@gmail.com", "pass");
        User user3 = new User("Olya", "Ivanen", "olya@gmail.com", "pass");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        ToDo toDo1 = new ToDo("todo1", LocalDateTime.of(2024, 12, 12, 15, 15), user1);
        ToDo toDo2 = new ToDo("todo2", LocalDateTime.of(2024, 12, 12, 15, 15), user2);
        ToDo toDo3 = new ToDo("todo3", LocalDateTime.of(2024, 12, 12, 15, 15), user3);

        toDoService.addTodo(toDo1, user1);
        toDoService.addTodo(toDo2, user1);
        toDoService.addTodo(toDo1, user2);
        toDoService.addTodo(toDo3, user3);
        toDoService.addTodo(toDo2, user3);
        ToDo expected = toDo1;
        ToDo actual = toDoService.getByUserTitle(user1, "todo1");
        Assertions.assertEquals(expected, actual);
    }
}
