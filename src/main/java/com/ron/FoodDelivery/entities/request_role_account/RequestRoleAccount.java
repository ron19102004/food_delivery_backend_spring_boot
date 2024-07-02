package com.ron.FoodDelivery.entities.request_role_account;

import com.ron.FoodDelivery.entities.EntityBase;
import com.ron.FoodDelivery.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "requests_role_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRoleAccount extends EntityBase {
    @Column(nullable = false)
    private RequestRole role;
    @Column(nullable = false)
    private RequestStatus status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date handled_at;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date requested_at;
    @ColumnDefault("false")
    private Boolean is_accepted;
    //relationships
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
}
