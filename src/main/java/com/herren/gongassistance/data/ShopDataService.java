package com.herren.gongassistance.data;

import com.herren.gongassistance.base.exception.GongAssistanceCode;
import com.herren.gongassistance.base.exception.GongAssistanceException;
import com.herren.gongassistance.shop.domain.entity.Shop;
import com.herren.gongassistance.shop.domain.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopDataService {
    private final ShopRepository repository;

    public Shop findById(Long shopId) {
        return repository.findById(shopId).orElseThrow(() -> new GongAssistanceException(GongAssistanceCode.E0002));
    }

    public Shop save(Shop entity) {
        return repository.save(entity);
    }

    public Shop findByIdFetchJoin(Long id) {
        return Optional.ofNullable(repository.findByIdFetchJoin(id)).orElseThrow(() -> new GongAssistanceException(GongAssistanceCode.E0002));
    }

    public Optional<Shop> findByBizNo(String bizNo) {
        return repository.findByBizNo(bizNo);
    }
}
