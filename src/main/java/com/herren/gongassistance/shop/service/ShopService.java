package com.herren.gongassistance.shop.service;

import com.herren.gongassistance.base.exception.GongAssistanceCode;
import com.herren.gongassistance.base.exception.GongAssistanceException;
import com.herren.gongassistance.base.lock.RedisLockService;
import com.herren.gongassistance.data.EmployeeDataService;
import com.herren.gongassistance.data.ShopDataService;
import com.herren.gongassistance.employee.domain.dto.EmployeeInfo;
import com.herren.gongassistance.employee.domain.entity.Employee;
import com.herren.gongassistance.shop.domain.constants.ShopStatus;
import com.herren.gongassistance.shop.domain.dto.ShopAddRequest;
import com.herren.gongassistance.shop.domain.dto.ShopInfo;
import com.herren.gongassistance.shop.domain.dto.ShopUpdateRequest;
import com.herren.gongassistance.shop.domain.entity.Shop;
import com.herren.gongassistance.utils.LockKeyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopDataService shopDataService;
    private final EmployeeDataService employeeDataService;

    private final RedisLockService redisLockService;

    public void addShop(ShopAddRequest shopRequest) {
        redisLockService.runWithLock(LockKeyFactory.createShopKey(shopRequest.getBizNo()), 500L, () -> {
            shopDataService.findByBizNo(shopRequest.getBizNo()).ifPresent(it -> {
                throw new GongAssistanceException(GongAssistanceCode.E0005);
            });

            shopDataService.save(Shop.builder()
                    .shopName(shopRequest.getShopName())
                    .bizNo(shopRequest.getBizNo())
                    .status(ShopStatus.READY)
                    .tel(shopRequest.getTel())
                    .kakaotalkId(shopRequest.getKakaotalkId())
                    .build());
        });
    }

    @Transactional
    public void updateShopStatus(Long shopId, ShopUpdateRequest request) {

        Shop shopEntity = shopDataService.findById(shopId);

        shopEntity.setStatus(request.getStatus());

        // 상태가 "삭제" 로 바뀌면 해당 샵의 사업자번호, 연락처, 카카오톡 아이디를 삭제합니다
        if(request.getStatus().equals(ShopStatus.DELETE)) {
            shopEntity.setBizNo(null);
            shopEntity.setTel(null);
            shopEntity.setKakaotalkId(null);
        }





        shopDataService.save(shopEntity);
    }


    public ShopInfo findByIdFetchJoin(Long id) {
        Shop shopEntity = shopDataService.findByIdFetchJoin(id);

        ShopInfo info = ShopInfo.builder()
                .id(shopEntity.getId())
                .bizNo(shopEntity.getBizNo())
                .shopName(shopEntity.getShopName())
                .tel(shopEntity.getTel())
                .kakaotalkId(shopEntity.getKakaotalkId())
                .status(shopEntity.getStatus())
                .createdAt(shopEntity.getCreatedAt())
                .updatedAt(shopEntity.getUpdatedAt())
                .build();

        if(!shopEntity.getEmployees().isEmpty()) {
            info.setEmployees(shopEntity.getEmployees().stream()
                    .map(it -> EmployeeInfo.builder()
                            .id(it.getId())
                            .kakaotalkId(it.getKakaotalkId())
                            .tel(it.getTel())
                            .status(it.getStatus())
                            .name(it.getName())
                            .createdAt(it.getCreatedAt())
                            .updatedAt(it.getUpdatedAt())
                            .build())
                    .collect(Collectors.toList()));
        }

        return info;

    }

    public void deleteShop(Long shopId) {
        Shop shopEntity = shopDataService.findById(shopId);

        shopEntity.setStatus(ShopStatus.DELETE);
        // 상태가 "삭제" 로 바뀌면 해당 샵의 사업자번호, 연락처, 카카오톡 아이디를 삭제합니다
        shopEntity.setBizNo(null);
        shopEntity.setTel(null);
        shopEntity.setKakaotalkId(null);

        shopDataService.save(shopEntity);
    }
}
