package com.ron.FoodDelivery.entities.seller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ron.FoodDelivery.entities.food.FoodEntity;
import com.ron.FoodDelivery.entities.location.LocationEntity;
import com.ron.FoodDelivery.entities.rating.RatingEntity;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.entities.voucher.VoucherEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.List;

@Builder
@Entity
@Table(name = "sellers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerEntity {
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    private Double latitude;
    private Double longitude;
    @Column(nullable = false)
    private String phone_number;
    @Column(nullable = false)
    private String email;
    private String background_image;
    private String avatar;
    @Temporal(TemporalType.TIME)
    private Date open_at;
    @Temporal(TemporalType.TIME)
    private Date close_at;
    @Temporal(TemporalType.TIMESTAMP)
    private Date issued_at;
    @ColumnDefault("false")
    private Boolean enabled;
    //relationships;
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private UserEntity user;
    @JsonIgnore
    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY,orphanRemoval = true)
    private List<RatingEntity> ratingEntities;
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private LocationEntity location;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seller",orphanRemoval = true)
    private List<FoodEntity> foodEntities;
    @JsonIgnore
    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY,orphanRemoval = true)
    private List<VoucherEntity> voucherEntities;

}
