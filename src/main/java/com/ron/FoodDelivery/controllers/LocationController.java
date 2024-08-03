package com.ron.FoodDelivery.controllers;

import com.ron.FoodDelivery.entities.location.LocationEntity;
import com.ron.FoodDelivery.entities.location.dto.RequestCreateLocationDto;
import com.ron.FoodDelivery.entities.location.dto.RequestUpdateLocationDto;
import com.ron.FoodDelivery.services.LocationService;
import com.ron.FoodDelivery.utils.PreAuthUtil;
import com.ron.FoodDelivery.utils.ResponseLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping("")
    public ResponseEntity<ResponseLayout<List<LocationEntity>>> getAllLocation() {
        return ResponseEntity.ok(new ResponseLayout<>(locationService.findAll(), "Found!", true));
    }

    @PostMapping("/new")
//    @PreAuthorize(PreAuthUtil.hasADMIN)
    public ResponseEntity<ResponseLayout<Object>> createLocation(@RequestBody RequestCreateLocationDto requestCreateLocationDto) {
        locationService.create(requestCreateLocationDto);
        return ResponseEntity.ok(new ResponseLayout<>(null, "Created!", true));
    }

    @PutMapping("/{id}")
    @PreAuthorize(PreAuthUtil.hasADMIN)
    public ResponseEntity<ResponseLayout<Object>> updateLocation(
            @PathVariable("id") Long id,
            @RequestBody RequestUpdateLocationDto requestCreateLocationDto) {
        locationService.update(id, requestCreateLocationDto);
        return ResponseEntity.ok(new ResponseLayout<>(null, "Updated!", true));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(PreAuthUtil.hasADMIN)
    public ResponseEntity<ResponseLayout<Object>> remove(@PathVariable("id") Long id) {
        locationService.remove(id);
        return ResponseEntity.ok(new ResponseLayout<>(null, "Removed!", true));
    }
}
