package com.ron.FoodDelivery.entities.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ron.FoodDelivery.entities.EntityBase;
import com.ron.FoodDelivery.entities.assessment.AssessmentEntity;
import com.ron.FoodDelivery.entities.comment.CommentEntity;
import com.ron.FoodDelivery.entities.deliver.DeliverEntity;
import com.ron.FoodDelivery.entities.delivery_charges.DeliveryChargesEntity;
import com.ron.FoodDelivery.entities.food.FoodEntity;
import com.ron.FoodDelivery.entities.process.ProcessEntity;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.entities.voucher.VoucherEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity extends EntityBase {
    @Column(nullable = false,unique = true)
    private String code_order;
    @Column(nullable = false)
    private Double latitude_receive;
    @Column(nullable = false)
    private Double longitude_receive;
    @Column(nullable = false)
    private Double latitude_send;
    @Column(nullable = false)
    private Double longitude_send;
    @Column(nullable = false)
    private Integer quantity;
    @Column(columnDefinition = "TEXT")
    private String note;
    @Column(columnDefinition = "TEXT")
    private String reason_cancel;
    @Column(nullable = false)
    private OrderStatus status;
    @Column(nullable = false)
    private Double total;
    @Column(nullable = false)
    private Double kilometer;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String address;
    //relationships
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "deliver_id", referencedColumnName = "id")
    private DeliverEntity deliver;
    @ManyToOne
    @JoinColumn(name = "delivery_charges_id", referencedColumnName = "id")
    private DeliveryChargesEntity delivery_charges;
    @ManyToOne
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    private VoucherEntity voucher;
    @ManyToOne
    @JoinColumn(name = "food_id", referencedColumnName = "id", nullable = false)
    private FoodEntity food;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "order",orphanRemoval = true)
    private List<ProcessEntity> processEntities;
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "order")
    private AssessmentEntity assessmentEntity;
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "order")
    private CommentEntity commentEntity;
}
