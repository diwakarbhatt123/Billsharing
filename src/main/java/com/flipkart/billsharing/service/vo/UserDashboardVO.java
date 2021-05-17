package com.flipkart.billsharing.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDashboardVO {
    private int totalBalance;
    private List<GroupBalanceVO> groupBalanceVOList;
    private List<UserBalanceVO> userBalanceVOList;
}
