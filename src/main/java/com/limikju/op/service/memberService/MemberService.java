package com.limikju.op.service.memberService;

import com.limikju.op.domain.dto.MemberDTO.MemberInfoDto;
import com.limikju.op.domain.dto.MemberDTO.MemberSignUpDto;
import com.limikju.op.domain.dto.MemberDTO.MemberUpdateDto;

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

    MemberInfoDto getInfo(Long id) throws Exception;

    MemberInfoDto getMyInfo() throws Exception;
}
