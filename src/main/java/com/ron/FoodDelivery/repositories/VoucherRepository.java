package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.voucher.VoucherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<VoucherEntity, Long> {
    @Query("SELECT v FROM VoucherEntity v WHERE v.seller.user.username = :username AND v.hidden = :hidden AND CURRENT_TIMESTAMP < v.expired_at ORDER BY v.id DESC")
    List<VoucherEntity> findAllBySellerUsername(@Param("username") String username, @Param("hidden") Boolean hidden);
    VoucherEntity findByCodeAndHidden(String code,Boolean hidden);
    VoucherEntity findByIdAndHidden(Long id,Boolean hidden );
}
