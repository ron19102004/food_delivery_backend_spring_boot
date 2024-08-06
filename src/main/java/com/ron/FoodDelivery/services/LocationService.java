package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.entities.location.LocationEntity;
import com.ron.FoodDelivery.entities.location.dto.RequestCreateLocationDto;
import com.ron.FoodDelivery.entities.location.dto.RequestUpdateLocationDto;

import java.util.List;

public interface LocationService {
    List<LocationEntity> findAll();
    LocationEntity create(RequestCreateLocationDto requestCreateLocationDto);
    void remove(Long id);
    LocationEntity update(Long id, RequestUpdateLocationDto requestUpdateLocationDto);
}
