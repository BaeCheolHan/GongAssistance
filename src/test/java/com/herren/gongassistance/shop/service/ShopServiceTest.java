package com.herren.gongassistance.shop.service;

import com.herren.gongassistance.base.exception.GongAssistanceException;
import com.herren.gongassistance.base.lock.RedisLockService;
import com.herren.gongassistance.data.ShopDataService;
import com.herren.gongassistance.shop.domain.constants.ShopStatus;
import com.herren.gongassistance.shop.domain.dto.ShopAddRequest;
import com.herren.gongassistance.shop.domain.dto.ShopUpdateRequest;
import com.herren.gongassistance.shop.domain.entity.Shop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    @Mock
    private ShopDataService shopDataService;

    /**
     *   @InjectMocks 을 한 상태에서 또 @InjectMocks 를 할 경우 @Spy 가 동작하지 않는다.
     *
     *    sut
     *    |- (inject) - shopDataService, redisLockService
     *                                         |- (inject) redisTemplate
     *
     *    과 같은 구조는 동작하지 않아 @Capture를 사용하여 redisLockService에 들어가는 runnable을 강제로 실행
     */

    @Mock
    private RedisLockService redisLockService;

    @InjectMocks
    private ShopService sut;

    @Captor
    private ArgumentCaptor<Runnable> captor;

    @Test
    @DisplayName("shop save 정상 케이스")
    void happyCaseAddShop() {
        ShopAddRequest request = ShopAddRequest.builder()
                .shopName("test")
                .tel("123123123")
                .bizNo("1231231231")
                .kakaotalkId("test@naver.com")
                .build();

        Shop shop = Shop.builder()
                .bizNo("1231231231")
                .tel("123123123")
                .kakaotalkId("test@naver.com")
                .shopName("test")
                .status(ShopStatus.READY)
                .build();

        sut.addShop(request);

        Mockito.verify(redisLockService).runWithLock(any(), any(), captor.capture());
        captor.getValue().run();
        Mockito.verify(shopDataService).save(shop);
    }

    @Test
    @DisplayName("Shop 상태 Update 할때 존재하지 않는 식별자 호출 케이스")
    void unhappyUpdateShopStatus() {
        given(shopDataService.findById(any())).willThrow(GongAssistanceException.class);

        Assertions.assertThrows(GongAssistanceException.class, () -> {
            sut.updateShopStatus(1L, ShopUpdateRequest.builder().status(ShopStatus.NORMAL).build());
        });
    }

    @Test
    @DisplayName("Shop 상태 Update 정상 케이스")
    void happyUpdateShopStatus() {
        given(shopDataService.findById(any())).willReturn(Shop.builder()
                .id(1L)
                .status(ShopStatus.READY)
                .tel("01011112222")
                .shopName("test")
                .kakaotalkId("test@test.com")
                .bizNo("1234567890")
                .build());

        sut.updateShopStatus(1L, ShopUpdateRequest.builder().status(ShopStatus.NORMAL).build());

        Mockito.verify(shopDataService).findById(1L);

        Mockito.verify(shopDataService).save(Shop.builder()
                .id(1L)
                .status(ShopStatus.NORMAL)
                .tel("01011112222")
                .shopName("test")
                .kakaotalkId("test@test.com")
                .bizNo("1234567890")
                .build());
    }
}