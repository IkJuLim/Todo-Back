package com.limikju.op.service.todoService;

import com.limikju.op.domain.Todo;
import com.limikju.op.domain.dto.todoDto.TodoRequestDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface TodoService {

    Todo save(TodoRequestDTO.TodoAddDto todoAddDto);

    Todo findById(Long id) throws Exception;

    List<Todo> findAll();

    void deleteAll();

    void deleteTodo(Long todoId);

    Todo updateStatus(Long todoId, Boolean status);

    Todo update(Long todoId, TodoRequestDTO.UpdateDTO updateDto);
}

