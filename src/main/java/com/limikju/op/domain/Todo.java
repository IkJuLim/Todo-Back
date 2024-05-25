package com.limikju.op.domain;


import com.limikju.op.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Table(name="COMMENT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "checked")
    private boolean check;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner_id")
    private Member owner;

    private boolean isRemoved= false;

//    //== 연관관계 편의 메서드 ==//
//    public void confirmWriter(Member writer) {
//        this.owner = writer;
//        writer.addComment(this);
//    }
//
//    public void confirmPost(Post post) {
//        this.post = post;
//        post.addComment(this);
//    }
//
//    public void confirmParent(Comment parent){
//        this.parent = parent;
//        parent.addChild(this);
//    }
//
//    public void addChild(Comment child){
//        childList.add(child);
//    }
//
//    //== 수정 ==//
//    public void updateContent(String content) {
//        this.content = content;
//    }
    //== 삭제 ==//
    public void remove() {
        this.isRemoved = true;
    }
//
//    @Builder
//    public Comment( Member writer, Post post, Comment parent, String content) {
//        this.writer = writer;
//        this.post = post;
//        this.parent = parent;
//        this.content = content;
//        this.isRemoved = false;
//    }
//
//    //== 비즈니스 로직 ==//
//    //모든 자식 댓글이 삭제되었는지 판단
//    private boolean isAllChildRemoved() {
//        return getChildList().stream()
//                .map(Comment::isRemoved)//지워졌는지 여부로 바꾼다
//                .filter(isRemove -> !isRemove)//지워졌으면 true, 안지워졌으면 false이다. 따라서 filter에 걸러지는 것은 false인 녀석들이고, 있다면 false를 없다면 orElse를 통해 true를 반환한다.
//                .findAny()//지워지지 않은게 하나라도 있다면 false를 반환
//                .orElse(true);//모두 지워졌다면 true를 반환
//    }
}

