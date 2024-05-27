package com.limikju.op.controller;

import com.limikju.op.apiPayload.ApiResponse;
import com.limikju.op.apiPayload.code.status.SuccessStatus;
import com.limikju.op.converter.TodoConverter;
import com.limikju.op.domain.Todo;
import com.limikju.op.domain.dto.todoDto.TodoRequestDTO;
import com.limikju.op.domain.dto.todoDto.TodoResponseDTO;
import com.limikju.op.service.todoService.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ApiResponse<TodoResponseDTO.AddResponseDTO> addTodo(@Valid @RequestBody TodoRequestDTO.TodoAddDto todoAddDto) {
        Todo todo = todoService.save(todoAddDto);
        return ApiResponse.of(SuccessStatus.TODO_ADD, TodoConverter.toTodoAddResultDTO(todo));
    }

    @GetMapping("/{todoId}")
    public ApiResponse<TodoResponseDTO.GetResponseDTO> getTodo(@Valid @PathVariable Long todoId, HttpServletRequest httpServletRequest) throws Exception {
        Todo todo = todoService.findById(todoId);
        return ApiResponse.of(SuccessStatus.TODO_GET_ONE, TodoConverter.toTodoGetResultDTO(todo));
    }

    @GetMapping("/all")
    public ApiResponse<TodoResponseDTO.GetListResponseDTO> getTodoList(HttpServletRequest httpServletRequest) {
        List<Todo> todoList = todoService.findAll();
        return ApiResponse.of(SuccessStatus.TODO_GET_ALL, TodoConverter.toTodoGetListResultDTO(todoList));
    }

    @DeleteMapping("/{todoId}")
    public ApiResponse<TodoResponseDTO.DeleteOneResponseDTO> deleteTodo(@Valid @PathVariable Long todoId, HttpServletRequest httpServletRequest) throws Exception {
        todoService.deleteTodo(todoId);
        return ApiResponse.of(SuccessStatus.TODO_DELETE_ONE, TodoConverter.toDeleteOneResultDTO());
    }

    @DeleteMapping("/all")
    public ApiResponse<TodoResponseDTO.DeleteAllResponseDTO> deleteAllTodo(HttpServletRequest httpServletRequest) {
        todoService.deleteAll();
        return ApiResponse.of(SuccessStatus.TODO_DELETE_ALL, TodoConverter.toDeleteAllResultDTO());
    }

    @PatchMapping("/status/{todoId}")
    public ApiResponse<TodoResponseDTO.UpdateStatusResponseDTO> updateStatusTodo(@Valid @PathVariable Long todoId, @Valid @RequestBody TodoRequestDTO.UpdateStatusDTO updateStatusDto, HttpServletRequest httpServletRequest) throws Exception {
        Todo todo = todoService.updateStatus(todoId, updateStatusDto.getStatus());
        return ApiResponse.of(SuccessStatus.TODO_UPDATE_STATUS, TodoConverter.toUpdateStatusResultDTO(todo));
    }

    @PatchMapping("/{todoId}")
    public ApiResponse<TodoResponseDTO.UpdateResponseDTO> updateTodo(@Valid @PathVariable Long todoId, @Valid @RequestBody TodoRequestDTO.UpdateDTO updateDto, HttpServletRequest httpServletRequest) throws Exception {
        Todo todo = todoService.update(todoId, updateDto);
        return ApiResponse.of(SuccessStatus.TODO_UPDATE_DATA, TodoConverter.toUpdateResponseDTO(todo));
    }
}
