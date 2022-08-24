package com.herren.gongassistance.shop.domain.repository;

import com.herren.gongassistance.shop.domain.entity.Shop;

public interface ShopRepositoryCustom {
    Shop findByIdFetchJoin(Long id);
}
