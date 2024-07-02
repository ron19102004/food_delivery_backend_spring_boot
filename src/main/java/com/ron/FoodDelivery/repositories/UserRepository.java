package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username AND u.is_locked = :is_locked")
    Optional<UserEntity> findByUsernameAndIsLocked(@Param("username") String username, @Param("is_locked") Boolean is_locked);

    @Query("SELECT u FROM UserEntity u WHERE u.phone_number = :phone_number AND u.is_locked = :is_locked")
    UserEntity findByPhoneNumberAndIsLocked(@Param("phone_number") String phone_number, @Param("is_locked") Boolean is_locked);

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email AND u.is_locked = :is_locked")
    UserEntity findByEmailAndIsLocked(@Param("email") String email, @Param("is_locked") Boolean is_locked);
}
