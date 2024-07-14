package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.location.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    List<LocationEntity> findAllByDeleted(Boolean deleted);

    LocationEntity findByCodeAndDeleted(Integer code, Boolean deleted);

    LocationEntity findByIdAndDeleted(Long id, Boolean deleted);
}
