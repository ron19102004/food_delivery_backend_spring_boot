package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.request_role_account.RequestRoleAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRoleAccountRepository extends JpaRepository<RequestRoleAccount, Long> {

}
