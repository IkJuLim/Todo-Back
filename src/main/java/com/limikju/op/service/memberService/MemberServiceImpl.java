package com.limikju.op.service.memberService;

import com.limikju.op.apiPayload.code.status.ErrorStatus;
import com.limikju.op.apiPayload.exception.handler.MemberHandler;
import com.limikju.op.domain.Member;
import com.limikju.op.domain.dto.memberDTO.MemberRequestDTO;
import com.limikju.op.domain.dto.memberDTO.MemberResponseDTO;
import com.limikju.op.repository.MemberRepository;
import com.limikju.op.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member signUp(MemberRequestDTO.MemberSignUpDTO memberSignUpDto) {
        Member member = memberSignUpDto.toEntity();
        member.addUserAuthority();
        member.encodePassword(passwordEncoder);

        if(memberRepository.findByUsername(memberSignUpDto.username()).isPresent()){
            throw new MemberHandler(ErrorStatus.MEMBER_ALREADY_EXIST);
        }

        return memberRepository.save(member);
    }

    @Override
    public Member update(MemberRequestDTO.MemberUpdateDTO memberUpdateDto) {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        memberUpdateDto.age().ifPresent(member::updateAge);
        memberUpdateDto.name().ifPresent(member::updateName);
        memberUpdateDto.nickName().ifPresent(member::updateNickName);
        return member;
    }

    @Override
    public Member updatePassword(String checkPassword, String toBePassword) {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if(!member.matchPassword(passwordEncoder, checkPassword) ) {
            throw new MemberHandler(ErrorStatus.MEMBER_INVALID_PASSWORD);
        }
        member.updatePassword(passwordEncoder, toBePassword);

        return member;
    }

    @Override
    public Long withdraw(String checkPassword) {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Long member_id = member.getId();

        if(!member.matchPassword(passwordEncoder, checkPassword) ) {
            throw new MemberHandler(ErrorStatus.MEMBER_INVALID_PASSWORD);
        }

        memberRepository.delete(member);
        return member_id;
    }

    @Override
    public Member getMember() throws Exception {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("회원이 없습니다"));
        return member;
    }
}