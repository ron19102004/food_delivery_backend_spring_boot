package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.location.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    @Query("SELECT l FROM LocationEntity l WHERE l.deleted = :deleted  ORDER BY l.id ASC")
    List<LocationEntity> findAllByDeleted(@Param("deleted") Boolean deleted);

    LocationEntity findByCodeAndDeleted(String code, Boolean deleted);

    LocationEntity findByIdAndDeleted(Long id, Boolean deleted);
}
