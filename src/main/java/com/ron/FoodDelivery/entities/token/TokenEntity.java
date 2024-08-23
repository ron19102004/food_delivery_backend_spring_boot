package com.ron.FoodDelivery.entities.token;

import com.ron.FoodDelivery.entities.EntityBase;
import com.ron.FoodDelivery.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenEntity extends EntityBase {
    @Column(nullable = false)
    private String access_token;
    private UserAgent user_agent;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expired_at;
    //relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
}
