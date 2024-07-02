package com.ron.FoodDelivery.entities.rating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ron.FoodDelivery.entities.EntityBase;
import com.ron.FoodDelivery.entities.seller.SellerEntity;
import com.ron.FoodDelivery.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "ratings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingEntity extends EntityBase {
    private Integer value;
    //relationships
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "id", nullable = false)
    private SellerEntity seller;
}
