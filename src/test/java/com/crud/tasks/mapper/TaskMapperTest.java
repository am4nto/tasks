package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTest {

    @Autowired
    private TaskMapper mapper;

    @Test
    public void mapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(new Long(1), "test", "test content");

        //When
        Task task = mapper.mapToTask(taskDto);

        //Then
        assertEquals(new Long(1), task.getId());
        assertEquals("test", task.getTitle());
        assertEquals("test content", task.getContent());

    }

    @Test
    public void mapToTaskDto() {
        //Given
        Task task = new Task(new Long(1), "test", "test content");

        //When
        TaskDto taskDto = mapper.mapToTaskDto(task);

        //Then
        assertEquals(new Long(1), taskDto.getId());
        assertEquals("test", taskDto.getTitle());
        assertEquals("test content", taskDto.getContent());
    }

    @Test
    public void mapToTaskDtoList() {
        //Given
        List<Task> tasks = new ArrayList<>();
        Task task = new Task(new Long(1), "test", "test content");
        tasks.add(task);

        //When
        List<TaskDto> taskDtos = mapper.mapToTaskDtoList(tasks);

        //Then
        taskDtos.forEach(taskDto -> {
            assertEquals(new Long(1), taskDto.getId());
            assertEquals("test", taskDto.getTitle());
            assertEquals("test content", taskDto.getContent());
        });
    }
}