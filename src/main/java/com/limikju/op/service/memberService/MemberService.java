package com.limikju.op.service.memberService;

import com.limikju.op.domain.Member;
import com.limikju.op.domain.dto.memberDTO.MemberRequestDTO;
import com.limikju.op.domain.dto.memberDTO.MemberResponseDTO;

import java.util.Optional;

public interface MemberService {

    /**
     * 회원가입
     * 정보수정
     * 회원탈퇴
     * 정보조회
     */

    Member signUp(MemberRequestDTO.MemberSignUpDTO memberSignUpDto) throws Exception;

    Member update(MemberRequestDTO.MemberUpdateDTO memberUpdateDto) throws Exception;
    Member updatePassword(String checkPassword, String toBePassword) throws Exception;

    Long withdraw(String checkPassword) throws Exception;

    Member getMember() throws Exception;
}
