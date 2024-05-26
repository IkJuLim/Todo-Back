package com.limikju.op.service.todoService;

import com.limikju.op.apiPayload.code.status.ErrorStatus;
import com.limikju.op.apiPayload.exception.handler.MemberHandler;
import com.limikju.op.domain.Todo;
import com.limikju.op.domain.dto.todoDto.TodoAddDto;
import com.limikju.op.repository.MemberRepository;
import com.limikju.op.repository.TodoRepository;
import com.limikju.op.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
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
    public void save(TodoAddDto todoAddDto) {
        Todo todo = todoAddDto.toEntity();
        todo.confirmMember(
                memberRepository.findByUsername(
                        SecurityUtil.getLoginUsername()).orElseThrow(()
                        -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND)));
        todoRepository.save(todo);
    }

    @Override
    @Transactional(readOnly = true)
    public Todo findById(Long id) throws Exception {
        return todoRepository.findById(id).orElseThrow(() -> new Exception("todo가 없습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }
}
