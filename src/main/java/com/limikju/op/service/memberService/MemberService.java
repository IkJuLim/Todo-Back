package com.limikju.op.service.memberService;

import com.limikju.op.domain.dto.memberDTO.MemberInfoDto;
import com.limikju.op.domain.dto.memberDTO.MemberSignUpDto;
import com.limikju.op.domain.dto.memberDTO.MemberUpdateDto;

public interface MemberService {

    /**
     * 회원가입
     * 정보수정
     * 회원탈퇴
     * 정보조회
     */

    void signUp(MemberSignUpDto memberSignUpDto) throws Exception;

    void update(MemberUpdateDto memberUpdateDto) throws Exception;

    void updatePassword(String checkPassword, String toBePassword) throws Exception;

    void withdraw(String checkPassword) throws Exception;

    MemberInfoDto getMyInfo() throws Exception;
}
