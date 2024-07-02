package com.ron.FoodDelivery.entities.like;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ron.FoodDelivery.entities.EntityBase;
import com.ron.FoodDelivery.entities.food.FoodEntity;
import com.ron.FoodDelivery.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "likes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeEntity extends EntityBase {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "food_id", nullable = false, referencedColumnName = "id")
    private FoodEntity food;
}
