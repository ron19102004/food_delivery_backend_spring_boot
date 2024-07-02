package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.otp.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity,Long> {

}
