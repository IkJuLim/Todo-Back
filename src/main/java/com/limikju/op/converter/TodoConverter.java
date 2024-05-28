package com.limikju.op.converter;

import com.limikju.op.domain.Todo;
import com.limikju.op.domain.dto.todoDto.TodoResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class TodoConverter {
    public static TodoResponseDTO.AddResponseDTO toTodoAddResultDTO(Todo todo) {
        TodoResponseDTO.AddResponseDTO addResponseDTO =
                TodoResponseDTO.AddResponseDTO.builder()
                        .id(todo.getId())
                        .title(todo.getTitle())
                        .dueDate(todo.getDueDate())
                        .status(todo.isStatus())
                        .build();
        return addResponseDTO;
    }

    public static TodoResponseDTO.GetResponseDTO toTodoGetResultDTO(Todo todo) {
        TodoResponseDTO.GetResponseDTO getResponseDTO =
                TodoResponseDTO.GetResponseDTO.builder()
                        .id(todo.getId())
                        .title(todo.getTitle())
                        .dueDate(todo.getDueDate())
                        .status(todo.isStatus())
                        .build();
        return getResponseDTO;
    }

    public static TodoResponseDTO.GetListResponseDTO toTodoGetListResultDTO(List<Todo> todoList) {
        TodoResponseDTO.GetListResponseDTO getListResponseDTO =
                TodoResponseDTO.GetListResponseDTO.builder()
                        .todoList(todoList.stream().map(TodoConverter::toTodoGetResultDTO).collect(Collectors.toList()))
                        .build();
        return getListResponseDTO;
    }

    public static TodoResponseDTO.DeleteOneResponseDTO toDeleteOneResultDTO() {
        return null;
    }

    public static TodoResponseDTO.DeleteAllResponseDTO toDeleteAllResultDTO() {
        return null;
    }

    public static TodoResponseDTO.UpdateStatusResponseDTO toUpdateStatusResultDTO(Todo todo) {
        TodoResponseDTO.UpdateStatusResponseDTO updateStatusResponseDTO =
                TodoResponseDTO.UpdateStatusResponseDTO.builder()
                        .title(todo.getTitle())
                        .dueDate(todo.getDueDate())
                        .status(todo.isStatus())
                        .build();
        return updateStatusResponseDTO;
    }

    public static TodoResponseDTO.UpdateResponseDTO toUpdateResponseDTO(Todo todo) {
        TodoResponseDTO.UpdateResponseDTO updateResponseDTO =
                TodoResponseDTO.UpdateResponseDTO.builder()
                        .title(todo.getTitle())
                        .dueDate(todo.getDueDate())
                        .status(todo.isStatus())
                        .build();
        return updateResponseDTO;
    }
}
