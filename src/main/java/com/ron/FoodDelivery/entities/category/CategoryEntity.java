package com.ron.FoodDelivery.entities.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ron.FoodDelivery.entities.EntityBase;
import com.ron.FoodDelivery.entities.food.FoodEntity;
import com.ron.FoodDelivery.entities.voucher.VoucherEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity extends EntityBase {
    @Column(nullable = false)
    private String name;
    private String image;
    @ColumnDefault("false")
    private Boolean deleted;
    //relationships
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "category",orphanRemoval = true)
    private List<VoucherEntity> voucherEntities;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "category",orphanRemoval = true)
    private List<FoodEntity> foodEntities;
}
