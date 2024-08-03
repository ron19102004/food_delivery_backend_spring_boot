package com.ron.FoodDelivery.entities.deliver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ron.FoodDelivery.entities.assessment.AssessmentEntity;
import com.ron.FoodDelivery.entities.order.OrderEntity;
import com.ron.FoodDelivery.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.List;

@Builder
@Entity
@Table(name = "delivers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliverEntity {
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone_number;
    @Column(nullable = false)
    private String email;
    private String avatar;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date issued_at;
    @ColumnDefault("false")
    private Boolean enabled;
    //relationships;
    @MapsId
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private UserEntity user;
    @JsonIgnore
    @OneToMany(mappedBy = "deliver", fetch = FetchType.LAZY,orphanRemoval = true)
    private List<OrderEntity> orderEntities;
    @JsonIgnore
    @OneToMany(mappedBy = "deliver", fetch = FetchType.LAZY,orphanRemoval = true)
    private List<AssessmentEntity> assessmentEntities;
}
