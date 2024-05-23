package com.codesoft.edu;

import com.codesoft.edu.model.Priority;
import com.codesoft.edu.model.Task;
import com.codesoft.edu.model.ToDo;
import com.codesoft.edu.model.User;
import com.codesoft.edu.service.TaskService;
import com.codesoft.edu.service.ToDoService;
import com.codesoft.edu.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(JUnitPlatform.class)
public class TaskServiceTest {
    private static UserService userService;
    private static ToDoService toDoService;
    private static TaskService taskService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        toDoService = annotationConfigContext.getBean(ToDoService.class);
        taskService = annotationConfigContext.getBean(TaskService.class);
        annotationConfigContext.close();

        User user1 = new User("FirstName1", "LastName1", "email1@test.com", "password1");
        User user2 = new User("FirstName2", "LastName2", "email2@test.com", "password2");
        User user3 = new User("FirstName3", "LastName3", "email3@test.com", "password3");

        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        toDoService.addTodo(new ToDo("ToDo_User1", LocalDateTime.now()), user1);
        toDoService.addTodo(new ToDo("ToDo_User2", LocalDateTime.now()), user2);
        toDoService.addTodo(new ToDo("ToDo_User3.1", LocalDateTime.now()), user3);
        toDoService.addTodo(new ToDo("ToDo_User3.2", LocalDateTime.now()), user3);
        toDoService.addTodo(new ToDo("ToDo_User3.3", LocalDateTime.now()), user3);

    }

    @AfterEach
    public void clearAfterEachTest() {
        for (ToDo toDo : toDoService.getAll()) {
            toDo.getTasks().clear();
        }
    }

    @Test
    public void checkAddTask() {
        List<ToDo> toDoList = toDoService.getAll();

        //adding task with wrong parameter
        Assertions.assertNull(taskService.addTask(null, toDoList.get(0)));
        Assertions.assertNull(taskService.addTask(new Task("Task 2", Priority.HIGH), null));
        Assertions.assertEquals(0,taskService.getAll().size());

        //adding one task to each toDo
        int taskCount = 0;
        for (User user : userService.getAll()){
            for (ToDo toDo: toDoService.getByUser(user)){
                Assertions.assertNotNull(taskService.addTask(new Task("Task "+ ++taskCount, Priority.LOW), toDo));
            }
        }
        Assertions.assertEquals(5, taskService.getAll().size());

    }


    @Test
    public void checkUpdateTask() {
        List<ToDo> toDoList = toDoService.getAll();

        taskService.addTask(new Task("Task 1", Priority.LOW), toDoList.get(0));
        taskService.addTask(new Task("Task 2", Priority.MEDIUM), toDoList.get(0));
        taskService.addTask(new Task("Task 3", Priority.HIGH), toDoList.get(0));

        //updating three added tasks
        List<Task> taskList = taskService.getAll();
        for (Task task : taskList) {
            String nameBeforeUpdate = task.getName();
            Task updatedTask = taskService.updateTask(task);
            Assertions.assertNotNull(updatedTask);
            Assertions.assertEquals(updatedTask.getName(),nameBeforeUpdate+" updated");
        }

        //updating not added task
        Task notAddedTask = new Task("Not_added_task", Priority.LOW);
        Assertions.assertNull(taskService.updateTask(notAddedTask));
        Assertions.assertEquals("Not_added_task", notAddedTask.getName());

        //updating null task
        Assertions.assertNull(taskService.updateTask(null));

    }

    @Test
    public void checkDeleteTask() {
        List<ToDo> toDoList = toDoService.getAll();

        taskService.addTask(new Task("Task 1", Priority.LOW), toDoList.get(0));
        taskService.addTask(new Task("Task 2", Priority.MEDIUM), toDoList.get(0));
        taskService.addTask(new Task("Task 3", Priority.HIGH), toDoList.get(0));

        //delete task = null
        taskService.deleteTask(null);
        Assertions.assertEquals(3,taskService.getAll().size());

        //delete existing task
        Task findTask = taskService.getByToDoName(toDoList.get(0), "Task 1");
        Assertions.assertEquals(3,taskService.getAll().size());
        Assertions.assertNotNull(findTask, "Find First task");

        taskService.deleteTask(findTask);
        Assertions.assertEquals(2,taskService.getAll().size());
        Assertions.assertNull(taskService.getByToDoName(toDoList.get(0), "Task 1"), "Find already deleted task");

        //delete task again
        taskService.deleteTask(findTask);
        Assertions.assertEquals(2,taskService.getAll().size());

        //delete all other tasks
        for (Task task: taskService.getAll()){
            taskService.deleteTask(task);
        }
        Assertions.assertEquals(0, taskService.getAll().size());
        Assertions.assertNull(taskService.getByToDoName(toDoList.get(0), "Task 2"), "Find task already deleted");
        Assertions.assertNull(taskService.getByToDoName(toDoList.get(0), "Task 3"), "Find task already deleted");
    }


    @Test
    public void checkGetAll() {
        List<ToDo> toDoList = toDoService.getAll();

        //empty taskList
        Assertions.assertEquals(0, taskService.getAll().size());

        //three task in the list
        taskService.addTask(new Task("Task 1", Priority.LOW), toDoList.get(0));
        taskService.addTask(new Task("Task 2", Priority.MEDIUM), toDoList.get(0));
        taskService.addTask(new Task("Task 3", Priority.HIGH), toDoList.get(0));

        List<Task> tasks = taskService.getAll();

        Assertions.assertEquals(tasks.get(0).getName(), "Task 1");
        Assertions.assertEquals(tasks.get(1).getName(), "Task 2");
        Assertions.assertEquals(tasks.get(2).getName(), "Task 3");
        Assertions.assertEquals(3, taskService.getAll().size());

    }

    @Test
    public void checkGetByToDo() {

        List<ToDo> toDoList = toDoService.getAll();

        taskService.addTask(new Task("Task 1", Priority.LOW), toDoList.get(0));
        taskService.addTask(new Task("Task 2", Priority.MEDIUM), toDoList.get(1));
        taskService.addTask(new Task("Task 3.1", Priority.LOW), toDoList.get(2));
        taskService.addTask(new Task("Task 3.2", Priority.MEDIUM), toDoList.get(2));
        taskService.addTask(new Task("Task 3.3", Priority.HIGH), toDoList.get(2));

        //correct toDo
        List<Task> tasks = taskService.getByToDo(toDoList.get(0));
        Assertions.assertEquals(1, tasks.size());
        Assertions.assertEquals(tasks.get(0), new Task("Task 1", Priority.LOW));

        tasks = taskService.getByToDo(toDoList.get(1));
        Assertions.assertEquals(1, tasks.size());
        Assertions.assertEquals(tasks.get(0), new Task("Task 2", Priority.MEDIUM));

        tasks = taskService.getByToDo(toDoList.get(2));
        Assertions.assertEquals(3, tasks.size());
        Assertions.assertEquals(tasks.get(0), new Task("Task 3.1", Priority.LOW));
        Assertions.assertEquals(tasks.get(1), new Task("Task 3.2", Priority.MEDIUM));
        Assertions.assertEquals(tasks.get(2), new Task("Task 3.3", Priority.HIGH));

        //wrong toDo = null
        Assertions.assertEquals(0, taskService.getByToDo(null).size());

        //wrong not existing toDo
        Assertions.assertEquals(0, taskService.getByToDo(new ToDo("TodoName",LocalDateTime.now(),null)).size());

    }

    @Test
    public void checkGetByToDoName() {
        List<ToDo> toDoList = toDoService.getAll();

        taskService.addTask(new Task("Task 1", Priority.LOW), toDoList.get(0));
        taskService.addTask(new Task("Task 2", Priority.MEDIUM), toDoList.get(1));
        taskService.addTask(new Task("Task 3.1", Priority.LOW), toDoList.get(2));
        taskService.addTask(new Task("Task 3.2", Priority.MEDIUM), toDoList.get(2));
        taskService.addTask(new Task("Task 3.3", Priority.HIGH), toDoList.get(2));

        //correct toDo and name
        Task findTask = taskService.getByToDoName(toDoList.get(2),"Task 3.2");
        Assertions.assertNotNull(findTask);
        Assertions.assertEquals(findTask.getName(), "Task 3.2");

        //wrong toDo
        Assertions.assertNull(taskService.getByToDoName(toDoList.get(1),"Task 3.2"));

        //wrong name
        Assertions.assertNull(taskService.getByToDoName(toDoList.get(2),"Task 1"));

        //wrong parameter
        Assertions.assertNull(taskService.getByToDoName(toDoList.get(2),null));
        Assertions.assertNull(taskService.getByToDoName(null,"Task 2"));

    }

    @Test
    public void checkGetByUserName() {
        List<ToDo> toDoList = toDoService.getAll();

        taskService.addTask(new Task("Task 1", Priority.LOW), toDoList.get(0));
        taskService.addTask(new Task("Task 2", Priority.MEDIUM), toDoList.get(1));
        taskService.addTask(new Task("Task 3.1", Priority.LOW), toDoList.get(2));
        taskService.addTask(new Task("Task 3.2", Priority.MEDIUM), toDoList.get(2));
        taskService.addTask(new Task("Task 3.3", Priority.HIGH), toDoList.get(2));

        //correct owner and name
        Task findTask = taskService.getByUserName(toDoList.get(2).getOwner(),"Task 3.2");
        Assertions.assertNotNull(findTask);
        Assertions.assertEquals(findTask.getName(), "Task 3.2");

        //wrong name
        Assertions.assertNull(taskService.getByUserName(toDoList.get(2).getOwner(),"Task 2"));

        //wrong owner
        Assertions.assertNull(taskService.getByUserName(toDoList.get(0).getOwner(),"Task 2"));

        //wrong parameter
        Assertions.assertNull(taskService.getByUserName(toDoList.get(0).getOwner(),null));
        Assertions.assertNull(taskService.getByUserName(null,"Task 2"));
    }

}
