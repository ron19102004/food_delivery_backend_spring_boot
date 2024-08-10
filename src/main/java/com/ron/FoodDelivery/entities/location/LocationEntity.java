package com.ron.FoodDelivery.entities.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ron.FoodDelivery.entities.EntityBase;
import com.ron.FoodDelivery.entities.seller.SellerEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "locations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationEntity extends EntityBase {
    @Column(nullable = false)
    private String name;
    @ColumnDefault("false")
    private Boolean deleted;
    @Column(nullable = false)
    private String code;
    //relationships
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "location",orphanRemoval = true)
    private List<SellerEntity> sellerEntities;
}
