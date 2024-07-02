package com.ron.FoodDelivery.entities.delivery_charges;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ron.FoodDelivery.entities.EntityBase;
import com.ron.FoodDelivery.entities.order.OrderEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "delivery_charges")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryChargesEntity extends EntityBase {
    @Column(nullable = false)
    private Double price;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date issued_at;
    @Column(nullable = false)
    private double kilometer;
    @ColumnDefault("false")
    private Boolean is_active;
    //relationships
    @JsonIgnore
    @OneToMany(mappedBy = "delivery_charges", fetch = FetchType.LAZY,orphanRemoval = true)
    private List<OrderEntity> orderEntities;
}
