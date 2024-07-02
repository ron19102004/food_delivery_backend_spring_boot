package com.ron.FoodDelivery.entities.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ron.FoodDelivery.entities.EntityBase;
import com.ron.FoodDelivery.entities.category.CategoryEntity;
import com.ron.FoodDelivery.entities.comment.CommentEntity;
import com.ron.FoodDelivery.entities.like.LikeEntity;
import com.ron.FoodDelivery.entities.order.OrderEntity;
import com.ron.FoodDelivery.entities.seller.SellerEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "foods")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodEntity extends EntityBase {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private Double price;
    private Double sale_price;
    private Float sale_off;
    private Long sold;
    private String poster;
    @ColumnDefault("false")
    private Boolean deleted;
    //relationships
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private CategoryEntity category;
    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id", nullable = false)
    private SellerEntity seller;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "food",orphanRemoval = true)
    private List<LikeEntity> likeEntities;
    @JsonIgnore
    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY,orphanRemoval = true)
    private List<OrderEntity> orderEntities;
    @JsonIgnore
    @OneToMany(mappedBy = "food",  fetch = FetchType.LAZY,orphanRemoval = true)
    private List<CommentEntity> commentEntities;
}
