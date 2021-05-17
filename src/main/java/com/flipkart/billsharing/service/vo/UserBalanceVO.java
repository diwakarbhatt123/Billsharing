package com.flipkart.billsharing.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBalanceVO {
    private String name;
    private int balance;
}
