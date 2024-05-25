package com.limikju.op.service.todoService;

import com.limikju.op.domain.Todo;

import java.util.List;

public interface todoService {

    void save(Todo comment);

    Todo findById(Long id) throws Exception;

    List<Todo> findAll();
}

