package com.codesoft.edu.service;

import java.util.List;

import com.codesoft.edu.model.Task;
import com.codesoft.edu.model.ToDo;
import com.codesoft.edu.model.User;

public interface TaskService {
    
    Task addTask(Task task, ToDo todo);

    Task updateTask(Task task);

    void deleteTask(Task task);

    List<Task> getAll();

    List<Task> getByToDo(ToDo todo);

    Task getByToDoName(ToDo todo, String name);

    Task getByUserName(User user, String name);
    
}
