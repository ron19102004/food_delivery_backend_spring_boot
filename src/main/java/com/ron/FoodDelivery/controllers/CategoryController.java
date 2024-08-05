package com.ron.FoodDelivery.controllers;

import com.ron.FoodDelivery.entities.category.CategoryEntity;
import com.ron.FoodDelivery.entities.category.dto.RequestCreateCategoryDto;
import com.ron.FoodDelivery.entities.category.dto.RequestUpdateCategoryDto;
import com.ron.FoodDelivery.services.CategoryService;
import com.ron.FoodDelivery.utils.PreAuthUtil;
import com.ron.FoodDelivery.utils.ResponseLayout;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<ResponseLayout<List<CategoryEntity>>> getAll() {
        return ResponseEntity.ok(new ResponseLayout<>(categoryService.getAll(), "Got!", true));
    }
    @PreAuthorize(PreAuthUtil.hasADMIN)
    @PostMapping("/new")
    public ResponseEntity<ResponseLayout<CategoryEntity>> create(@RequestBody RequestCreateCategoryDto requestCreateCategoryDto) {
        return ResponseEntity.ok(new ResponseLayout<>(categoryService.create(requestCreateCategoryDto), "Created!", true));
    }

    @PreAuthorize(PreAuthUtil.hasADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<ResponseLayout<CategoryEntity>> uploadImage(@PathVariable("id") Long id,
                                                                      @NotNull @RequestBody RequestUpdateCategoryDto requestUpdateCategoryDto) {
        return ResponseEntity.ok(new ResponseLayout<>(categoryService.updateImage(id, requestUpdateCategoryDto), "Uploaded!", true));
    }

    @PreAuthorize(PreAuthUtil.hasADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseLayout<Object>> remove(@PathVariable("id") Long id) {
        categoryService.remove(id);
        return ResponseEntity.ok(new ResponseLayout<>(null, "Removed", true));
    }
}
