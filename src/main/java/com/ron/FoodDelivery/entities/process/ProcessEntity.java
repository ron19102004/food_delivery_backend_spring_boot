package com.ron.FoodDelivery.entities.process;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ron.FoodDelivery.entities.EntityBase;
import com.ron.FoodDelivery.entities.order.OrderEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "processes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessEntity extends EntityBase {
    @Column(nullable = false)
    private ProcessStatus status;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String note;
    //relationships
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private OrderEntity order;
}
