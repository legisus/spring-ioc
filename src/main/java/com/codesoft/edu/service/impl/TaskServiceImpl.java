package com.codesoft.edu.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.codesoft.edu.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codesoft.edu.model.Task;
import com.codesoft.edu.model.ToDo;
import com.codesoft.edu.model.User;
import com.codesoft.edu.service.ToDoService;

@Service
public class TaskServiceImpl implements TaskService {

    private ToDoService toDoService;

    @Autowired
    public TaskServiceImpl(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    public Task addTask(Task task, ToDo todo) {
        if (task == null || todo == null || getAll().contains(task)) {
            return null;
        } else {
            return todo.getTasks().add(task) ? task : null;
        }
    }

    @Override
    public Task updateTask(Task task) {
        return toDoService.getAll()
                .stream()
                .map(ToDo::getTasks)//
                .filter(x -> x.contains(task))
                .flatMap(List::stream)
                .filter(x -> x.equals(task))
                .findFirst()
                .map(x -> {
                    x.setName(x.getName() + " updated");
                    return x;
                })
                .orElse(null);
    }

    @Override
    public void deleteTask(Task task) {
        for (ToDo toDo : toDoService.getAll()) {
            if (toDo.getTasks().remove(task)) {
                break;
            }
        }
    }

    @Override
    public List<Task> getAll() {
        return toDoService.getAll()
                .stream()
                .map(ToDo::getTasks)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getByToDo(ToDo todo) {
        return (todo == null) ? new ArrayList<>() : todo.getTasks();
    }

    @Override
    public Task getByToDoName(ToDo todo, String taskName) {
        if (todo == null || taskName == null) {
            return null;
        } else {
            return todo.getTasks()
                    .stream()
                    .filter(x -> x.getName().compareTo(taskName) == 0)
                    .findFirst()
                    .orElse(null);
        }
    }

    @Override
    public Task getByUserName(User user, String taskName) {
        if (user == null || taskName == null) {
            return null;
        } else {
            return toDoService.getAll()
                    .stream()
                    .filter(x -> x.getOwner().equals(user))
                    .map(ToDo::getTasks)
                    .flatMap(List::stream)
                    .filter(x -> x.getName().compareTo(taskName) == 0)
                    .findFirst()
                    .orElse(null);
        }
    }

}
