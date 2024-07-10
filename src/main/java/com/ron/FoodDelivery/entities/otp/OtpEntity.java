package com.ron.FoodDelivery.entities.otp;

import com.ron.FoodDelivery.entities.EntityBase;
import com.ron.FoodDelivery.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Entity
@Table(name = "otps")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpEntity {
    @Id
    private Long id;
    @Column(nullable = false)
    private String code;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expired_at;
    //relationships
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id",referencedColumnName = "id", nullable = false)
    private UserEntity user;
}
