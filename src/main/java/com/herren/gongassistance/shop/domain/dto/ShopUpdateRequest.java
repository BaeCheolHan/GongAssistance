package com.herren.gongassistance.shop.domain.dto;

import com.herren.gongassistance.shop.domain.constants.ShopStatus;
import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShopUpdateRequest {
    @NotNull
    private ShopStatus status;
}
