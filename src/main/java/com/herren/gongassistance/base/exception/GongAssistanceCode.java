package com.herren.gongassistance.base.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GongAssistanceCode {

    INTERNAL_ERROR("서버 내부 오류 입니다.", GongAssistanceErrorLevel.CRITICAL),
    E0001("입력값이 잘못 되었습니다.", GongAssistanceErrorLevel.ERROR),
    E0002("존재하지 않는 식별키입니다.", GongAssistanceErrorLevel.WARN),
    E0003("이미 샵에 등록된 직원입니다.", GongAssistanceErrorLevel.WARN),
    E0004("파라미터 오류입니다.", GongAssistanceErrorLevel.WARN),
    E0005("이미 등록된 매장입니다.", GongAssistanceErrorLevel.WARN),
    E0006("삭제된 회원정보입니다.", GongAssistanceErrorLevel.WARN),
    E0007("삭제된 샵입니다.", GongAssistanceErrorLevel.WARN),
    E0008("이미 등록된 사용자(직원) 정보입니다.", GongAssistanceErrorLevel.WARN),
    SUCCESS("SUCCESS", null);

    private final String message;
    private final GongAssistanceErrorLevel errorLevel;

}
