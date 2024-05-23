package com.codesoft.edu.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Component
@Scope("Prototype")
public class ToDo {

    private String title;
    private LocalDateTime createdAt;
    private User owner;
    private List<Task> tasks = new ArrayList<>();

    public ToDo(String title, LocalDateTime createdAt) {
        this.title = title;
        this.createdAt = createdAt;
    }

    public ToDo(String title, LocalDateTime createdAt, User owner) {
        this(title, createdAt);
        this.owner = owner;
    }

    public ToDo() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks =  tasks;

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ToDo))
            return false;
        ToDo todo = (ToDo) obj;
        if (this.title == todo.title && this.owner.equals(todo.owner) &&
                this.createdAt == todo.createdAt &&
                this.tasks.equals(todo.tasks))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "title:" + title + "\n"
                + " date " + createdAt + "\n"
                + " user " + owner + "\n";
    }
}
