package com.limikju.op.service.memberService;

import com.limikju.op.converter.MemberConverter;
import com.limikju.op.domain.Member;
import com.limikju.op.domain.dto.memberDTO.MemberRequestDTO;
import com.limikju.op.domain.dto.memberDTO.MemberResponseDTO;
import com.limikju.op.domain.enums.Role;
import com.limikju.op.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @PersistenceContext EntityManager em;
    @Autowired
    MemberRepository memberRepository;

    @Autowired MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    String PASSWORD = "password";

    private void clear(){
        em.flush();
        em.clear();
    }

    private MemberRequestDTO.MemberSignUpDTO makeMemberSignUpDto() {
        return new MemberRequestDTO.MemberSignUpDTO("username",PASSWORD,"name","nickNAme",22);
    }

    private MemberRequestDTO.MemberSignUpDTO setMember() throws Exception {
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto = makeMemberSignUpDto();
        memberService.signUp(memberSignUpDto);
        clear();
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();

        emptyContext.setAuthentication(new UsernamePasswordAuthenticationToken(User.builder()
                .username(memberSignUpDto.username())
                .password(memberSignUpDto.password())
                .roles(Role.USER.name())
                .build(),
                null, null));

        SecurityContextHolder.setContext(emptyContext);
        return memberSignUpDto;
    }

    @AfterEach
    public void removeMember(){
        SecurityContextHolder.createEmptyContext().setAuthentication(null);
    }

    /**
     * 회원가입
     *    회원가입 시 아이디, 비밀번호, 이름, 별명, 나이를 입력하지 않으면 오류
     *    이미 존재하는 아이디가 있으면 오류
     *    회원가입 후 회원의 ROLE 은 USER
     *
     *
     */
    @Test
    public void 회원가입_성공() throws Exception {
        //given
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto = makeMemberSignUpDto();

        //when
        memberService.signUp(memberSignUpDto);
        clear();

        //then
        Member member = memberRepository.findByUsername(memberSignUpDto.username()).orElseThrow(() -> new Exception("회원이 없습니다"));
        assertThat(member.getId()).isNotNull();
        assertThat(member.getUsername()).isEqualTo(memberSignUpDto.username());
        assertThat(member.getName()).isEqualTo(memberSignUpDto.name());
        assertThat(member.getNickName()).isEqualTo(memberSignUpDto.nickName());
        assertThat(member.getAge()).isEqualTo(memberSignUpDto.age());
        assertThat(member.getRole()).isSameAs(Role.USER);

    }

    @Test
    public void 회원가입_실패_원인_아이디중복() throws Exception {
        //given
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto = makeMemberSignUpDto();
        memberService.signUp(memberSignUpDto);
        clear();

        //when, then TODO : MemberException으로 고쳐야 함
        assertThat(assertThrows(Exception.class, () -> memberService.signUp(memberSignUpDto)).getMessage()).isEqualTo("이미 존재하는 아이디입니다.");

    }


    @Test
    public void 회원가입_실패_입력하지않은_필드가있으면_오류() throws Exception {
        //given
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto1 = new MemberRequestDTO.MemberSignUpDTO(null,passwordEncoder.encode(PASSWORD),"name","nickNAme",22);
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto2 = new MemberRequestDTO.MemberSignUpDTO("username",null,"name","nickNAme",22);
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto3 = new MemberRequestDTO.MemberSignUpDTO("username",passwordEncoder.encode(PASSWORD),null,"nickNAme",22);
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto4 = new MemberRequestDTO.MemberSignUpDTO("username",passwordEncoder.encode(PASSWORD),"name",null,22);
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto5 = new MemberRequestDTO.MemberSignUpDTO("username",passwordEncoder.encode(PASSWORD),"name","nickNAme",null);

        //when, then
        assertThrows(Exception.class, () -> memberService.signUp(memberSignUpDto1));
        assertThrows(Exception.class, () -> memberService.signUp(memberSignUpDto2));
        assertThrows(Exception.class, () -> memberService.signUp(memberSignUpDto3));
        assertThrows(Exception.class, () -> memberService.signUp(memberSignUpDto4));
        assertThrows(Exception.class, () -> memberService.signUp(memberSignUpDto5));
    }


    /**
     * 회원정보수정
     * 회원가입을 하지 않은 사람이 정보수정시 오류 -> 시큐리티 필터가 알아서 막아줄거임
     * 아이디는 변경 불가능
     * 비밀번호 변경시에는, 현재 비밀번호를 입력받아서, 일치한 경우에만 바꿀 수 있음
     * 비밀번호 변경시에는 오직 비밀번호만 바꿀 수 있음
     *
     * 비밀번호가 아닌 이름,별명,나이 변경 시에는, 3개를 한꺼번에 바꿀 수도 있고, 한,두개만 선택해서 바꿀수도 있음
     * 아무것도 바뀌는게 없는데 변경요청을 보내면 오류
     *
     */



    @Test
    public void 회원수정_비밀번호수정_성공() throws Exception {
        //given
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto = setMember();


        //when
        String toBePassword = "1234567890!@#!@#";
        MemberRequestDTO.MemberUpdatePasswordDTO memberUpdatePasswordDTO = new MemberRequestDTO.MemberUpdatePasswordDTO(PASSWORD, toBePassword);
        memberService.updatePassword(memberUpdatePasswordDTO);
        clear();

        //then
        Member findMember = memberRepository.findByUsername(memberSignUpDto.username()).orElseThrow(() -> new Exception());
        assertThat(findMember.matchPassword(passwordEncoder, toBePassword)).isTrue();

    }



    @Test
    public void 회원수정_이름만수정() throws Exception {
        //given
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto = setMember();

        //when
        String updateName = "변경할래용";
        memberService.update(new MemberRequestDTO.MemberUpdateDTO(Optional.of(updateName),Optional.empty(), Optional.empty()));
        clear();

        //then
        memberRepository.findByUsername(memberSignUpDto.username()).ifPresent((member -> {
            assertThat(member.getName()).isEqualTo(updateName);
            assertThat(member.getAge()).isEqualTo(memberSignUpDto.age());
            assertThat(member.getNickName()).isEqualTo(memberSignUpDto.nickName());
        }));

    }
    @Test
    public void 회원수정_별명만수정() throws Exception {
        //given
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto = setMember();

        //when
        String updateNickName = "변경할래용";
        memberService.update(new MemberRequestDTO.MemberUpdateDTO(Optional.empty(), Optional.of(updateNickName), Optional.empty()));
        clear();

        //then
        memberRepository.findByUsername(memberSignUpDto.username()).ifPresent((member -> {
            assertThat(member.getNickName()).isEqualTo(updateNickName);
            assertThat(member.getAge()).isEqualTo(memberSignUpDto.age());
            assertThat(member.getName()).isEqualTo(memberSignUpDto.name());
        }));

    }

    @Test
    public void 회원수정_나이만수정() throws Exception {
        //given
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto = setMember();

        //when
        Integer updateAge = 33;
        memberService.update(new MemberRequestDTO.MemberUpdateDTO(Optional.empty(),  Optional.empty(), Optional.of(updateAge)));
        clear();

        //then
        memberRepository.findByUsername(memberSignUpDto.username()).ifPresent((member -> {
            assertThat(member.getAge()).isEqualTo(updateAge);
            assertThat(member.getNickName()).isEqualTo(memberSignUpDto.nickName());
            assertThat(member.getName()).isEqualTo(memberSignUpDto.name());
        }));
    }


    @Test
    public void 회원수정_이름별명수정() throws Exception {
        //given
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto = setMember();

        //when
        String updateNickName = "변경할래요옹";
        String updateName = "변경할래용";
        memberService.update(new MemberRequestDTO.MemberUpdateDTO(Optional.of(updateName),Optional.of(updateNickName),Optional.empty()));
        clear();

        //then
        memberRepository.findByUsername(memberSignUpDto.username()).ifPresent((member -> {
            assertThat(member.getNickName()).isEqualTo(updateNickName);
            assertThat(member.getName()).isEqualTo(updateName);

            assertThat(member.getAge()).isEqualTo(memberSignUpDto.age());
        }));

    }

    @Test
    public void 회원수정_이름나이수정() throws Exception {
        //given
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto = setMember();

        //when
        Integer updateAge = 33;
        String updateName = "변경할래용";
        memberService.update(new MemberRequestDTO.MemberUpdateDTO(Optional.of(updateName),Optional.empty(),Optional.of(updateAge)));
        clear();

        //then
        memberRepository.findByUsername(memberSignUpDto.username()).ifPresent((member -> {
            assertThat(member.getAge()).isEqualTo(updateAge);
            assertThat(member.getName()).isEqualTo(updateName);

            assertThat(member.getNickName()).isEqualTo(memberSignUpDto.nickName());
        }));


    }
    @Test
    public void 회원수정_별명나이수정() throws Exception {
        //given
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto = setMember();

        //when
        Integer updateAge = 33;
        String updateNickname = "변경할래용";
        memberService.update(new MemberRequestDTO.MemberUpdateDTO(Optional.empty(),Optional.of(updateNickname),Optional.of(updateAge)));
        clear();

        //then
        memberRepository.findByUsername(memberSignUpDto.username()).ifPresent((member -> {
            assertThat(member.getAge()).isEqualTo(updateAge);
            assertThat(member.getNickName()).isEqualTo(updateNickname);

            assertThat(member.getName()).isEqualTo(memberSignUpDto.name());
        }));

    }

    @Test
    public void 회원수정_이름별명나이수정() throws Exception {
        //given
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto = setMember();

        //when
        Integer updateAge = 33;
        String updateNickname = "변경할래용";
        String updateName = "변경할래용";
        memberService.update(new MemberRequestDTO.MemberUpdateDTO(Optional.of(updateName),Optional.of(updateNickname),Optional.of(updateAge)));
        clear();

        //then
        memberRepository.findByUsername(memberSignUpDto.username()).ifPresent((member -> {
            assertThat(member.getAge()).isEqualTo(updateAge);
            assertThat(member.getNickName()).isEqualTo(updateNickname);
            assertThat(member.getName()).isEqualTo(updateName);
        }));
    }

    /**
     * 회원탈퇴
     * 비밀번호를 입력받아서 일치하면 탈퇴 가능
     */

    @Test
    public void 회원탈퇴() throws Exception {
        //given
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto = setMember();

        //when
        memberService.withdraw(PASSWORD);

        //then
        assertThat(assertThrows(Exception.class, ()-> memberRepository.findByUsername(memberSignUpDto.username()).orElseThrow(() -> new Exception("회원이 없습니다"))).getMessage()).isEqualTo("회원이 없습니다");

    }

    @Test
    public void 회원탈퇴_실패_비밀번호가_일치하지않음() throws Exception {
        //given
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto = setMember();

        //when, then TODO : MemberException으로 고쳐야 함
        assertThat(assertThrows(Exception.class ,() -> memberService.withdraw(PASSWORD+"1")).getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.");

    }

    @Test
    public void 내정보조회() throws Exception {
        //given
        MemberRequestDTO.MemberSignUpDTO memberSignUpDto = setMember();

        //when
        Member member = memberService.getMember();
        MemberResponseDTO.MemberInfoDTO myInfo = MemberConverter.toMemberInfoDTO(member);

        //then
        assertThat(myInfo.getUsername()).isEqualTo(memberSignUpDto.username());
        assertThat(myInfo.getName()).isEqualTo(memberSignUpDto.name());
        assertThat(myInfo.getAge()).isEqualTo(memberSignUpDto.age());
        assertThat(myInfo.getNickName()).isEqualTo(memberSignUpDto.nickName());

    }

}