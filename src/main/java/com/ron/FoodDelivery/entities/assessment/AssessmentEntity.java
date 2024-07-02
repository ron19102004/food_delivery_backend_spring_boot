package com.ron.FoodDelivery.entities.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ron.FoodDelivery.entities.deliver.DeliverEntity;
import com.ron.FoodDelivery.entities.order.OrderEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Builder
@Entity
@Table(name = "assessments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentEntity {
    @Id
    private Long id;
    @Column(nullable = false)
    private Integer rating;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    //relationships
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id",nullable = false)
    private OrderEntity order;
    @ManyToOne
    @JoinColumn(name = "deliver_id", referencedColumnName = "id", nullable = false)
    private DeliverEntity deliver;


}
