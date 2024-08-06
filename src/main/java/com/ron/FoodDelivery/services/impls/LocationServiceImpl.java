package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.entities.location.LocationEntity;
import com.ron.FoodDelivery.entities.location.dto.RequestCreateLocationDto;
import com.ron.FoodDelivery.entities.location.dto.RequestUpdateLocationDto;
import com.ron.FoodDelivery.exceptions.ServiceException;
import com.ron.FoodDelivery.repositories.LocationRepository;
import com.ron.FoodDelivery.services.LocationService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<LocationEntity> findAll() {
        return locationRepository.findAllByDeleted(false);
    }

    @Override
    public LocationEntity create(RequestCreateLocationDto requestCreateLocationDto) {
        LocationEntity location = locationRepository.findByCodeAndDeleted(requestCreateLocationDto.code(), false);
        if (location != null) throw new ServiceException("Location already exist!", HttpStatus.BAD_REQUEST);
        location = LocationEntity.builder()
                .code(requestCreateLocationDto.code())
                .name(requestCreateLocationDto.name())
                .deleted(false)
                .build();
      return  locationRepository.save(location);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        LocationEntity location = locationRepository.findByIdAndDeleted(id, false);
        if (location == null) throw new ServiceException("Location not found", HttpStatus.NOT_FOUND);
        location.setDeleted(true);
        entityManager.merge(location);
    }

    @Transactional
    @Override
    public LocationEntity update(Long id, RequestUpdateLocationDto requestUpdateLocationDto) {
        LocationEntity locationById = locationRepository.findByIdAndDeleted(id, false);
        if (locationById == null) throw new ServiceException("Location not found", HttpStatus.NOT_FOUND);
        LocationEntity locationByCode = locationRepository.findByCodeAndDeleted(requestUpdateLocationDto.code(), false);
        if (locationByCode != null && !Objects.equals(locationByCode.getId(), id))
            throw new ServiceException("Code is exist!", HttpStatus.BAD_REQUEST);
        locationById.setName(requestUpdateLocationDto.name());
        locationById.setCode(requestUpdateLocationDto.code());
        entityManager.merge(locationById);
        return locationById;
    }
}
