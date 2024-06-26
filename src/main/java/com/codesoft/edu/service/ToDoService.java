package com.codesoft.edu.service;

import java.util.List;

import com.codesoft.edu.model.ToDo;
import com.codesoft.edu.model.User;

public interface ToDoService {
    
    ToDo addTodo(ToDo todo, User user);

    ToDo updateTodo(ToDo todo);

    void deleteTodo(ToDo todo);

    List<ToDo> getAll();

    List<ToDo> getByUser(User user);

    ToDo getByUserTitle(User user, String title);
    
}
