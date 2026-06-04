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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


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

    private CreateToDoDto createToDoDto;

    private UpdateToDoDto updateToDoDto;

    @BeforeEach
    public void init() {
        toDo = ToDo.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

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
        given(toDoService.createToDo(ArgumentMatchers.any())).willReturn(ToDoResponse.fromToDo(toDo));

        ResultActions response = mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createToDoDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
