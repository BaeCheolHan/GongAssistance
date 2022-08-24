package com.herren.gongassistance.shop.domain.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ShopAddRequest {
    @NotNull
    private String shopName;
    @NotNull
    private String bizNo;
    @NotNull
    private String tel;
    private String kakaotalkId;

}
