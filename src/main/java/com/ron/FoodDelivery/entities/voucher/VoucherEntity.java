package com.ron.FoodDelivery.entities.voucher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ron.FoodDelivery.entities.EntityBase;
import com.ron.FoodDelivery.entities.category.CategoryEntity;
import com.ron.FoodDelivery.entities.order.OrderEntity;
import com.ron.FoodDelivery.entities.seller.SellerEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "vouchers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherEntity extends EntityBase {
    @Column(nullable = false)
    private Float percent;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    @ColumnDefault("false")
    private Boolean is_active;
    @Column(nullable = false)
    private Long quantity;
    @Column(nullable = false)
    private Long quantity_current;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date issued_at;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expired_at;
    @ColumnDefault("false")
    private Boolean hidden;
    @ColumnDefault("false")
    private Boolean of_system;
    //relationships
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "id", nullable = false)
    private SellerEntity seller;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private CategoryEntity category;
    @JsonIgnore
    @OneToMany(mappedBy = "voucher", fetch = FetchType.LAZY,orphanRemoval = true)
    private List<OrderEntity> orderEntities;
}
