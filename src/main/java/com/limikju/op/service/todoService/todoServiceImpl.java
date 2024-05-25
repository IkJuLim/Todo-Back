package com.limikju.op.service.todoService;

import com.limikju.op.domain.Todo;
import com.limikju.op.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class todoServiceImpl implements todoService {

    private final TodoRepository todoRepository;


    @Override
    public void save(Todo todo) {
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
