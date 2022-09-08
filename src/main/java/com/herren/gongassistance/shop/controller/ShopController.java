package com.herren.gongassistance.shop.controller;

import com.herren.gongassistance.base.response.BaseResponse;
import com.herren.gongassistance.base.exception.GongAssistanceCode;
import com.herren.gongassistance.shop.domain.dto.ShopAddRequest;
import com.herren.gongassistance.shop.domain.dto.ShopInfo;
import com.herren.gongassistance.shop.domain.dto.ShopUpdateRequest;
import com.herren.gongassistance.shop.service.ShopService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Api(value = "Shop API")
@RequestMapping(path = "shop")
public class ShopController {

    private final ShopService shopService;



    @GetMapping(path="{shopId}")
    public ShopInfo getShop(@PathVariable(name="shopId") Long shopId) {
        return shopService.findByIdFetchJoin(shopId);
    }

    @PostMapping
    public BaseResponse addShop(@Validated @RequestBody ShopAddRequest shopRequest) {
        shopService.addShop(shopRequest);
        return BaseResponse.builder().code(GongAssistanceCode.SUCCESS).message(GongAssistanceCode.SUCCESS.getMessage()).build();
    }

    @PutMapping(path = "{shopId}")
    public BaseResponse updateShopStatus(@Validated @RequestBody ShopUpdateRequest request, @PathVariable(name = "shopId") Long shopId) {
        shopService.updateShopStatus(shopId, request);
        return BaseResponse.builder().code(GongAssistanceCode.SUCCESS).message(GongAssistanceCode.SUCCESS.getMessage()).build();
    }

    @DeleteMapping
    public BaseResponse deleteShop(@PathVariable(name = "shopId") Long shopId) {
        shopService.deleteShop(shopId);

        return BaseResponse.builder().code(GongAssistanceCode.SUCCESS).message(GongAssistanceCode.SUCCESS.getMessage()).build();
    }
}
