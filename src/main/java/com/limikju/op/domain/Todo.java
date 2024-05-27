package com.limikju.op.domain;


import com.limikju.op.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name="TODO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @Column(name = "status")
    private boolean status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner")
    private Member owner;

    private String title;

    private LocalDate dueDate;

    private boolean isRemoved= false;

    //== 수정 ==//
    public void updateTodo(String title, LocalDate dueDate) {
        this.title = title;
        this.dueDate = dueDate;
    }
    public void updateTodoStatus(Boolean status) {
        this.status = status;
    }

    //== 삭제 ==//
    public void remove() {
        this.isRemoved = true;
    }
    @Builder
    public Todo(Member owner, String title, LocalDate dueDate) {
        this.status = false;
        this.owner = owner;
        this.title = title;
        this.dueDate = dueDate;
        this.isRemoved = false;
    }

    public void confirmMember(Member member) {
        this.owner = member;
        member.addTodo(this);
    }
}

