package com.limikju.op.repository;

import com.limikju.op.domain.Member;
import com.limikju.op.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {


    List<Todo> findAllByOwnerOrderByCreatedAtDesc(Member member);
    List<Todo> findAllByOwnerOrderByTitleDesc(Member member);
    List<Todo> findAllByOwnerOrderByDueDateDesc(Member member);
    List<Todo> findAllByOwnerOrderByCreatedAtAsc(Member member);
    List<Todo> findAllByOwnerOrderByTitleAsc(Member member);
    List<Todo> findAllByOwnerOrderByDueDateAsc(Member member);
    Optional<Todo> findByIdAndOwner(Long id, Member member);

    void deleteAllByOwner(Member member);

    void deleteByIdAndOwner(Long todoId, Member member);
}

