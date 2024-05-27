package com.limikju.op.repository;

import com.limikju.op.domain.Member;
import com.limikju.op.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByOwner(Member member);

    Optional<Todo> findByIdAndOwner(Long id, Member member);

    void deleteAllByOwner(Member member);

    void deleteByIdAndOwner(Long todoId, Member member);
}

