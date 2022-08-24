package com.herren.gongassistance.base.exception;

import lombok.Getter;

@Getter
public class GongAssistanceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final GongAssistanceCode errorCode;

    public GongAssistanceException(String message, GongAssistanceCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public GongAssistanceException(GongAssistanceCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }


}
