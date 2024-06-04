package com.limikju.op.service.todoService;

import com.limikju.op.domain.Todo;
import com.limikju.op.domain.dto.todoDto.TodoRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TodoService {

    Todo save(TodoRequestDTO.TodoAddDto todoAddDto);

    Todo findById(Long id) throws Exception;

    List<Todo> findAllTodo(String sort);

    void deleteAll();

    void deleteTodo(Long todoId);

    Todo updateStatus(Long todoId, Boolean status);

    Todo update(Long todoId, TodoRequestDTO.UpdateDTO updateDto);
}

