package com.example.todolists.todo.controller;

import com.example.todolists.configuration.SecurityConfiguration;
import com.example.todolists.domain.common.ToDoStatus;
import com.example.todolists.domain.todo.controller.ToDoController;
import com.example.todolists.domain.todo.dto.CreateToDoDto;
import com.example.todolists.domain.todo.dto.ToDoResponse;
import com.example.todolists.domain.todo.dto.UpdateToDoDto;
import com.example.todolists.domain.todo.entity.ToDo;
import com.example.todolists.domain.todo.service.ToDoService;
import com.example.todolists.security.JwtUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(
        controllers = ToDoController.class,
        excludeAutoConfiguration = SecurityConfiguration.class
)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ToDoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ToDoService toDoService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtUtils jwtUtils;

    private ToDo toDo;

    private UUID randomToDoUuid;

    private CreateToDoDto createToDoDto;

    private ToDoResponse toDoResponse;

    private UpdateToDoDto updateToDoDto;

    @BeforeEach
    public void init() {
        randomToDoUuid = UUID.randomUUID();

        toDo = ToDo.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        toDoResponse = ToDoResponse.fromToDo(toDo);

        createToDoDto = CreateToDoDto.builder()
                .title("Task")
                .description("This is a task")
                .dueTime(new Date())
                .status(ToDoStatus.TODO)
                .build();

        updateToDoDto = UpdateToDoDto.builder()
                .title(Optional.of("New task"))
                .description(Optional.of("update"))
                .build();
    }

    @Test
    public void ToDoController_Create_ReturnCreated() throws Exception {
        given(toDoService.createToDo(any())).willReturn(ToDoResponse.fromToDo(toDo));

        ResultActions response = mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createToDoDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(createToDoDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(createToDoDto.getDescription())));
    }

    @Test
    public void ToDoController_FindAll_ReturnResponse() throws Exception {
        List<ToDoResponse> responseDtos = new ArrayList<>();
        responseDtos.add(ToDoResponse.fromToDo(toDo));
        responseDtos.add(ToDoResponse.fromToDo(toDo));
        when(toDoService.findAll()).thenReturn(responseDtos);

        ResultActions response = mockMvc.perform(get("/todos"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(responseDtos.size())));
    }

    @Test
    public void ToDoController_FindById_ReturnResponse() throws Exception {
        when(toDoService.findById(randomToDoUuid)).thenReturn(toDoResponse);

        ResultActions response = mockMvc.perform(get("/todos/" + randomToDoUuid.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoResponse)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(createToDoDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(createToDoDto.getDescription())));
    }

    @Test
    public void ToDoController_UpdateById_ReturnResponse() throws Exception {
        ToDoResponse updatedResponse = ToDoResponse.builder()
                .id(randomToDoUuid)
                .title(updateToDoDto.getTitle().get())
                .description(updateToDoDto.getDescription().get())
                .build();

        when(toDoService.updateToDo(eq(randomToDoUuid), any(UpdateToDoDto.class)))
                .thenReturn(updatedResponse);

        ResultActions response = mockMvc.perform(patch("/todos/" + randomToDoUuid.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateToDoDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(updateToDoDto.getTitle().get())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(updateToDoDto.getDescription().get())));
    }

    @Test
    public void ToDoController_DeleteById_ReturnNoContent() throws Exception {
        doNothing().when(toDoService).deleteToDoById(randomToDoUuid);

        ResultActions response = mockMvc.perform(delete("/todos/" + randomToDoUuid.toString())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
