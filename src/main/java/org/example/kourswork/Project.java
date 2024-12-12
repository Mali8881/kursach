package org.example.kourswork;
import java.util.ArrayList;
import java.util.List;
import org.example.kourswork.EnhancedCalendar.Task;


public class Project {
    private String projectName;
    private List<Task> tasks;

    public Project(String projectName) {
        this.projectName = projectName;
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    // Getterы и Setterы
}
