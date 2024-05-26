package com.limikju.op.controller;

import com.limikju.op.domain.dto.todoDto.TodoAddDto;
import com.limikju.op.service.todoService.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public void addTodo(@Valid @RequestBody TodoAddDto todoAddDto) {
        todoService.save(todoAddDto);
    }
}
