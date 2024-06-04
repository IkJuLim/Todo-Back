package com.limikju.op.service.todoService;

import com.limikju.op.apiPayload.code.status.ErrorStatus;
import com.limikju.op.apiPayload.exception.handler.MemberHandler;
import com.limikju.op.apiPayload.exception.handler.TodoHandler;
import com.limikju.op.domain.Member;
import com.limikju.op.domain.Todo;
import com.limikju.op.domain.dto.todoDto.TodoRequestDTO;
import com.limikju.op.repository.MemberRepository;
import com.limikju.op.repository.TodoRepository;
import com.limikju.op.util.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;


    @Override
    public Todo save(TodoRequestDTO.TodoAddDto todoAddDto) {
        Todo todo = todoAddDto.toEntity();
        todo.confirmMember(
                memberRepository.findByUsername(
                        SecurityUtil.getLoginUsername()).orElseThrow(()
                        -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND)));
        return todoRepository.save(todo);
    }

    @Override
    @Transactional(readOnly = true)
    public Todo findById(Long id) throws Exception {
        String username = SecurityUtil.getLoginUsername();
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Todo todo = todoRepository.findByIdAndOwner(id, member).orElseThrow(() -> new TodoHandler(ErrorStatus.TODO_NOT_FOUND));
        return todo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Todo> findAllTodo(String orderBy) {
        String username = SecurityUtil.getLoginUsername();
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        return switch (orderBy) {
            case "Decrease : Title" -> todoRepository.findAllByOwnerOrderByTitleDesc(member);
            case "Increase : Title" -> todoRepository.findAllByOwnerOrderByTitleAsc(member);
            case "Decrease : Due Date" -> todoRepository.findAllByOwnerOrderByDueDateDesc(member);
            case "Increase : Due Date" -> todoRepository.findAllByOwnerOrderByDueDateAsc(member);
            case "Decrease : Create At" -> todoRepository.findAllByOwnerOrderByCreatedAtAsc(member);
            case "Increase : Create At" -> todoRepository.findAllByOwnerOrderByCreatedAtDesc(member);
            default -> throw new TodoHandler(ErrorStatus.TODO_ORDER_BY_INVALID);
        };
    }

    @Override
    public void deleteAll() {
        String username = SecurityUtil.getLoginUsername();
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        todoRepository.deleteAllByOwner(member);
    }

    @Override
    public void deleteTodo(Long todoId) {
        String username = SecurityUtil.getLoginUsername();
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        todoRepository.deleteByIdAndOwner(todoId, member);
    }

    @Override
    public Todo updateStatus(Long todoId, Boolean status) {
        String username = SecurityUtil.getLoginUsername();
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Todo todo = todoRepository.findByIdAndOwner(todoId, member).orElseThrow(() -> new TodoHandler(ErrorStatus.TODO_NOT_FOUND));
        todo.updateTodoStatus(status);
        return todo;
    }

    @Override
    public Todo update(Long todoId, TodoRequestDTO.UpdateDTO updateDto) {
        String username = SecurityUtil.getLoginUsername();
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Todo todo = todoRepository.findByIdAndOwner(todoId, member).orElseThrow(() -> new TodoHandler(ErrorStatus.TODO_NOT_FOUND));
        todo.updateTodo(updateDto.getTitle(), updateDto.getDueDate());
        return todo;
    }
}
