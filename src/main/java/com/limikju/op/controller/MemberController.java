package com.limikju.op.controller;

import com.limikju.op.apiPayload.ApiResponse;
import com.limikju.op.apiPayload.code.status.ErrorStatus;
import com.limikju.op.apiPayload.code.status.SuccessStatus;
import com.limikju.op.apiPayload.exception.handler.MemberHandler;
import com.limikju.op.converter.MemberConverter;
import com.limikju.op.domain.Member;
import com.limikju.op.domain.dto.memberDTO.*;
import com.limikju.op.service.memberService.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     */
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<MemberResponseDTO.SignUpResultDTO> signUp(@Valid @RequestBody MemberRequestDTO.MemberSignUpDTO memberSignUpDto, HttpServletResponse response) throws Exception {
        Member member = memberService.signUp(memberSignUpDto);
        return ApiResponse.of(SuccessStatus.MEMBER_JOIN, MemberConverter.toSignUpResultDTO(member));
    }

    /**
     * 회원정보수정
     */
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<MemberResponseDTO.UpdateResultDTO> updateBasicInfo(@Valid @RequestBody MemberRequestDTO.MemberUpdateDTO memberUpdateDto) throws Exception {
        Member member = memberService.update(memberUpdateDto);
        return ApiResponse.of(SuccessStatus.MEMBER_UPDATE, MemberConverter.toUpdateResultDTO(member));
    }

    /**
     * 회원탈퇴
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<MemberResponseDTO.WithdrawResultDTO> withdraw(@Valid @RequestBody MemberRequestDTO.MemberWithdrawDTO memberWithdrawDto) throws Exception {
        Long member_id = memberService.withdraw(memberWithdrawDto.checkPassword());

        return ApiResponse.of(SuccessStatus.MEMBER_DELETE, MemberConverter.toWithdrawResultDTO(member_id));
    }

    /**
     * 내정보조회
     */
    @GetMapping
    public ApiResponse<MemberResponseDTO.MemberInfoDTO> getMyInfo() throws Exception {
        Member member = memberService.getMember();

        return ApiResponse.of(SuccessStatus.MEMBER_FOUND, MemberConverter.toMemberInfoDTO(member));
    }
}


