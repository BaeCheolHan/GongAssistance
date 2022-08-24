package com.herren.gongassistance.shop.domain.repository;

import com.herren.gongassistance.shop.domain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long>, ShopRepositoryCustom {
    Optional<Shop> findByBizNo(String bizNo);
}
