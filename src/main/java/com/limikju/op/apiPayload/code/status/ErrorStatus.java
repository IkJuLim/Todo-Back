package com.limikju.op.apiPayload.code.status;

import com.limikju.op.apiPayload.code.BaseErrorCode;
import com.limikju.op.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),


    // 멤버 관려 에러
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4001", "존재하지 않는 사용자 입니다."),
    MEMBER_INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "MEMBER4002", "비밀번호가 옳지 않습니다."),
    MEMBER_ALREADY_EXIST(HttpStatus.CONFLICT, "MEMBER4003", "이미 존재하는 사용자입니다."),
    MEMBER_PASSWORD_NOT_MATCHED(HttpStatus.NOT_ACCEPTABLE, "MEMBER4002", "비밀번호가 옳지 않습니다."),

    // TODO 관련 에러
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "TODO4001", "존재하지 않는 TODO 입니다."),
    TODO_ORDER_BY_INVALID(HttpStatus.NOT_ACCEPTABLE, "TODO4002", "옳바르지 않은 orderBy 입니다.");



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
