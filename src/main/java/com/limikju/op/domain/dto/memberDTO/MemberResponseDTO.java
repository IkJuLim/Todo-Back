package com.limikju.op.domain.dto.memberDTO;

import com.limikju.op.domain.Member;
import lombok.*;

import java.time.LocalDateTime;

public class MemberResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpResultDTO {
        private Long member_id;
        private LocalDateTime created_at;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateResultDTO {
        private Long member_id;
        private String name;
        private String nickName;
        private int age;
        private LocalDateTime update_at;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WithdrawResultDTO {
        private Long member_id;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberInfoDTO {
        private Long member_id;

        private String name;
        private String nickName;
        private String username;
        private Integer age;



        @Builder
        public MemberInfoDTO(Member member) {
            this.member_id = member.getId();
            this.name = member.getName();
            this.nickName = member.getNickName();
            this.username = member.getUsername();
            this.age = member.getAge();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResultDTO {

        private String accessToken;
        private String refreshToken;

    }
}
