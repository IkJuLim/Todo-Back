package com.limikju.op.service.todoService;

import com.limikju.op.domain.Todo;
import com.limikju.op.domain.dto.todoDto.TodoAddDto;

import java.util.List;

public interface TodoService {

    void save(TodoAddDto todoAddDto);

    Todo findById(Long id) throws Exception;

    List<Todo> findAll();
}

