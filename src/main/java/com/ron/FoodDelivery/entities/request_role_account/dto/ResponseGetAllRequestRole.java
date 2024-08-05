package com.ron.FoodDelivery.entities.request_role_account.dto;

import com.ron.FoodDelivery.entities.request_role_account.RequestRoleAccount;

import java.util.List;

public record ResponseGetAllRequestRole(
        List<RequestRoleAccount> handled,
        List<RequestRoleAccount> waiting
) {
}
