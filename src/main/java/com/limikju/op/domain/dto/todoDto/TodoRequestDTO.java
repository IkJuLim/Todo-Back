package com.limikju.op.domain.dto.todoDto;

import com.limikju.op.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class TodoRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TodoAddDto {

        private String title;

        private LocalDate dueDate;

        public Todo toEntity() {
            return Todo.builder()
                    .title(this.title)
                    .dueDate(this.dueDate)
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TodoGetDto {
        private Long id;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateStatusDTO {
        private Boolean status;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateDTO {

        private String title;

        private LocalDate dueDate;
    }
}
