package com.codesoft.edu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codesoft.edu.model.ToDo;
import com.codesoft.edu.model.User;
import com.codesoft.edu.service.ToDoService;
import com.codesoft.edu.service.UserService;

@Service
public class ToDoServiceImpl implements ToDoService {

    private UserService userService;

    @Autowired
    public ToDoServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public ToDo addTodo(ToDo todo, User user) {
        List<User> users = userService.getAll();
        for (User u : users) {
            if (u.equals(user)) {
                if (u.getMyTodos() == null) {
                    u.setMyTodos(new ArrayList<>());
                }
                u.getMyTodos().add(todo);
                return todo;
            }
        }
        return null;
    }

    public ToDo updateTodo(ToDo todoToUpdate) {
        final User user = todoToUpdate.getOwner();
        final List<ToDo> toDoList = user.getMyTodos();

        if (toDoList == null) {
            return null;
        }

        for (ToDo existingTodo : toDoList) {
            if (existingTodo.getTitle().equals(todoToUpdate.getTitle())) {
                existingTodo.setTasks(todoToUpdate.getTasks());
                existingTodo.setCreatedAt(todoToUpdate.getCreatedAt());
                return existingTodo;
            }
        }

        return null;
    }

    public void deleteTodo(ToDo todo) {
        User user = todo.getOwner();
        List<ToDo> todos = user.getMyTodos();
        for (ToDo toDo : todos) {
            if (toDo.equals(todo)) {
                todos.remove(todo);
                break;
            }
        }


    }

    public List<ToDo> getAll() {
        List<User> users = userService.getAll();
        List<ToDo> toDoList = new ArrayList<>();
        for (User user : users) {
            List<ToDo> list = user.getMyTodos();
            toDoList.addAll(list);
        }
        return toDoList;
    }

    public List<ToDo> getByUser(User user) {
        if (user == null)
            return null;
        List<ToDo> toDoList = user.getMyTodos();
        return toDoList;

    }

    public ToDo getByUserTitle(User user, String title) {
        List<ToDo> toDoList = user.getMyTodos();
        for (ToDo toDo : toDoList)
            if (toDo.getTitle().equals(title))
                return toDo;

        return null;
    }

}
