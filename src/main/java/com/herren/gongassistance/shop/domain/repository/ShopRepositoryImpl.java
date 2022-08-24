package com.herren.gongassistance.shop.domain.repository;

import com.herren.gongassistance.employee.domain.entity.QEmployee;
import com.herren.gongassistance.shop.domain.entity.QShop;
import com.herren.gongassistance.shop.domain.entity.Shop;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ShopRepositoryImpl extends QuerydslRepositorySupport implements ShopRepositoryCustom {

    public ShopRepositoryImpl() {
        super(ShopRepositoryCustom.class);
    }

    @Override
    public Shop findByIdFetchJoin(Long id) {

        QShop shop = QShop.shop;
        QEmployee employee = QEmployee.employee;

        return from(shop)
                .leftJoin(shop.employees, employee)
                .where(shop.id.eq(id))
                .fetchJoin()
                .fetchOne();
    }
}
