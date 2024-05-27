package com.limikju.op.converter;

import com.limikju.op.domain.Member;
import com.limikju.op.domain.dto.memberDTO.MemberResponseDTO;

public class MemberConverter {
    public static MemberResponseDTO.SignUpResultDTO toSignUpResultDTO(Member member) {
        return MemberResponseDTO.SignUpResultDTO.builder()
                .member_id(member.getId())
                .created_at(member.getCreatedAt())
                .build();
    }

    public static MemberResponseDTO.UpdateResultDTO toUpdateResultDTO(Member member) {
        return MemberResponseDTO.UpdateResultDTO.builder()
                .member_id(member.getId())
                .name(member.getName())
                .nickName(member.getNickName())
                .age(member.getAge())
                .update_at(member.getUpdatedAt())
                .build();
    }

    public static MemberResponseDTO.WithdrawResultDTO toWithdrawResultDTO(Long member_id) {
        return MemberResponseDTO.WithdrawResultDTO.builder()
                .member_id(member_id)
                .build();
    }

    public static MemberResponseDTO.LoginResultDTO toLoginResultDTO(String accessToken, String refreshToken) {
        return MemberResponseDTO.LoginResultDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static MemberResponseDTO.MemberInfoDTO toMemberInfoDTO(Member member) {
        return MemberResponseDTO.MemberInfoDTO.builder()
                .member_id(member.getId())
                .name(member.getName())
                .nickName(member.getNickName())
                .username(member.getUsername())
                .age(member.getAge())
                .build();
    }
}
