package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {

    @InjectMocks
    private DbService service;

    @Mock
    private TaskRepository repository;


    @Test
    public void ShouldReturnEmptyList() {
        //Given
        when(repository.findAll()).thenReturn(new ArrayList<>());

        //When
        List<Task> tasks = service.getAllTasks();

        //Then
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void ShouldReturnTasks() {
        //Given
        Task task = new Task(new Long(1), "test", "test task");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        when(repository.findAll()).thenReturn(tasks);

        //When
        List<Task> returnedTasks = service.getAllTasks();

        //Then
        assertEquals(1, returnedTasks.size());
        returnedTasks.forEach(task1 -> {
            assertEquals(new Long(1), task.getId());
            assertEquals("test", task.getTitle());
            assertEquals("test task", task.getContent());
        });
    }

    @Test
    public void ShouldReturnEmpty() {
        //Given
        when(repository.findById(new Long(1))).thenReturn(Optional.empty());

        //When
        Optional<Task> optionalTask = service.getTask(new Long(1));

        //Then
        assertFalse(optionalTask.isPresent());
    }

    @Test
    public void ShouldReturnTask() {
        //Given
        Task task = new Task(new Long(1), "test", "test task");
        when(repository.findById(new Long(1))).thenReturn(Optional.of(task));

        //When
        Optional<Task> optionalTask = service.getTask(new Long(1));

        //Then
        assertTrue(optionalTask.isPresent());
        assertEquals(new Long(1), optionalTask.get().getId());
        assertEquals("test", optionalTask.get().getTitle());
        assertEquals("test task", optionalTask.get().getContent());
    }

    @Test
    public void ShouldReturnSavedTask() {
        //Given
        Task task = new Task(new Long(1), "test", "test task");
        when(repository.save(task)).thenReturn(task);

        //When
        Task returnedTask = service.saveTask(task);

        //Then
        assertEquals(new Long(1), returnedTask.getId());
        assertEquals("test", returnedTask.getTitle());
        assertEquals("test task", returnedTask.getContent());
    }
}