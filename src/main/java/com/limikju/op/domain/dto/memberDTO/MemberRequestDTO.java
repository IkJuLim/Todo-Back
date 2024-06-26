package com.limikju.op.domain.dto.memberDTO;

import com.limikju.op.domain.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

import java.util.Optional;

public class MemberRequestDTO {

    public static record UpdatePasswordDTO(@NotBlank(message = "비밀번호를 입력해주세요")
                                    String checkPassword,

                                           @NotBlank(message = "비밀번호를 입력해주세요")
                                    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
                                            message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
                                    String toBePassword) {
    }


    public static record MemberWithdrawDTO(@NotBlank(message = "비밀번호를 입력해주세요")
                                    String checkPassword) {
    }

    public static record MemberUpdateDTO(

            @Size(min=2, message = "사용자 이름이 너무 짧습니다.")
            @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "사용자 이름은 한글 또는 알파벳만 입력해주세요.")
            Optional<String> name,

            @Size(min=2, message = "닉네임이 너무 짧습니다.")
            Optional<String> nickName,

            @Range(min = 0, max = 150, message = "0 이상 150 이하 값을 입력 하세요.")
            Optional<Integer> age) {
    }

    public static record MemberSignUpDTO(@NotBlank(message = "아이디를 입력해주세요") @Size(min = 7, max = 25, message = "아이디는 7~25자 내외로 입력해주세요")
                                  String username,

                                         @NotBlank(message = "비밀번호를 입력해주세요")
                                  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
                                          message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
                                  String password,

                                         @NotBlank(message = "이름을 입력해주세요") @Size(min=2, message = "사용자 이름이 너무 짧습니다.")
                                  @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "사용자 이름은 한글 또는 알파벳만 입력해주세요.")
                                  String name,

                                         @NotBlank(message = "닉네임을 입력해주세요.")
                                  @Size(min=2, message = "닉네임이 너무 짧습니다.")
                                  @NotBlank String nickName,


                                         @NotNull(message = "나이를 입력해주세요")
                                  @Range(min = 0, max = 150)
                                  Integer age) {

        public Member toEntity() {
            return Member.builder()
                    .username(username)
                    .password(password)
                    .name(name)
                    .nickName(nickName)
                    .age(age)
                    .build();
        }
    }

    public static record MemberUpdatePasswordDTO(@NotBlank(message = "비밀번호를 입력해주세요")
                                                 @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
                                                         message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
                                                 String checkPassword,

                                                 @NotBlank(message = "변경할 비밀번호를 입력해주세요")
                                                 @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
                                                         message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
                                                 String toBePassword){
    }
}
