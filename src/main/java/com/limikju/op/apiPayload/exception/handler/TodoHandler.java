package com.limikju.op.apiPayload.exception.handler;


import com.limikju.op.apiPayload.code.BaseErrorCode;
import com.limikju.op.apiPayload.exception.GeneralException;

public class TodoHandler extends GeneralException {
    public TodoHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
