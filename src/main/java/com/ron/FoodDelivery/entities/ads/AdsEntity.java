package com.ron.FoodDelivery.entities.ads;

import com.ron.FoodDelivery.entities.EntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "ads")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsEntity extends EntityBase {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date issued_at;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date expired_at;
    @Column(nullable = false)
    private String poster;
}
