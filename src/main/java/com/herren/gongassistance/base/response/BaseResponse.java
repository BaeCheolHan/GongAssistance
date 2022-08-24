package com.herren.gongassistance.base.response;

import com.herren.gongassistance.base.exception.GongAssistanceCode;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BaseResponse {
    private GongAssistanceCode code;
    private String message;
}
