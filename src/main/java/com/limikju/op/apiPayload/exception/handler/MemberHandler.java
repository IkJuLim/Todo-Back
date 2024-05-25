package com.limikju.op.apiPayload.exception.handler;


import com.limikju.op.apiPayload.code.BaseErrorCode;
import com.limikju.op.apiPayload.exception.GeneralException;

public class MemberHandler extends GeneralException {
    public MemberHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
