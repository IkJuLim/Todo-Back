package com.limikju.op.domain.dto.todoDto;

import com.limikju.op.domain.Todo;
import lombok.Getter;

import java.time.LocalDate;

public class TodoAddDto {

    private String title;

    private LocalDate dueDate;

    public Todo toEntity() {
        return Todo.builder()
                .title(this.title)
                .dueDate(this.dueDate)
                .build();
    }
}
