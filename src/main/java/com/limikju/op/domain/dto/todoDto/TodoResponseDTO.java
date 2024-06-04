package com.limikju.op.domain.dto.todoDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class TodoResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddResponseDTO {
        private Long id;
        private String title;
        private LocalDate dueDate;
        private Boolean status;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetResponseDTO {
        private Long id;
        private String title;
        private LocalDate dueDate;
        private Boolean status;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetListResponseDTO {
        List<GetResponseDTO> todoList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
//    @AllArgsConstructor
    public static class DeleteAllResponseDTO {
    }

    @Builder
    @Getter
    @NoArgsConstructor
//    @AllArgsConstructor
    public static class DeleteOneResponseDTO {
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateStatusResponseDTO {
        private Long id;
        private String title;
        private LocalDate dueDate;
        private Boolean status;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateResponseDTO {
        private Long id;
        private String title;
        private LocalDate dueDate;
        private Boolean status;
    }
}
