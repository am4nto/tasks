package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper mapper;

    private static Long ONE = new Long(1);

    @Test
    public void testGetTasks() throws Exception {
        //Given
        List<Task> tasks = new ArrayList<>();
        Task task = new Task((long) 1, "test", "test content");
        tasks.add(task);
        List<TaskDto> taskDtos = new ArrayList<>();
        TaskDto taskDto = new TaskDto((long) 1, "test", "test content");
        taskDtos.add(taskDto);

        when(service.getAllTasks()).thenReturn(tasks);
        when(mapper.mapToTaskDtoList(any(List.class))).thenReturn(taskDtos);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))

                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test")))
                .andExpect(jsonPath("$[0].content", is("test content")));
    }

    @Test
    public void testGetTask() throws Exception {
        //Given
        Task task = new Task((long) 1, "test", "test content");
        TaskDto taskDto = new TaskDto(ONE, "test", "test content");

        when(mapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(service.getTask((long) 1)).thenReturn(Optional.ofNullable(task));

        //When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test")))
                .andExpect(jsonPath("$.content", is("test content")));

    }

    @Test
    public void testDeleteTask() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                //.delete("/v1/task/deleteTask/{id}", "1")
                .delete("/v1/task/deleteTask/")
                .param("taskId","1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTask() throws Exception {
        //Given
        String createTaskDtoInJson = "{ \"id\": \"" + "1" + "\", " +
                "\"title\":\"" + "test" + "\"," +
                "\"content\":\"" + "content" + "\"}}";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/v1/task/updateTask/")
                .contentType(MediaType.APPLICATION_JSON)
                .content (createTaskDtoInJson);

        //When & Then
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
    }

    @Test
    public void testCreateTask() throws Exception {

        //Given
        String createTaskDtoInJson = "{ \"id\": \"" + "1" + "\", " +
                "\"title\":\"" + "test" + "\"," +
                "\"content\":\"" + "content" + "\"}}";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/v1/task/createTask/")
                .contentType(MediaType.APPLICATION_JSON)
                .content (createTaskDtoInJson);

        //When & Then
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
    }
}