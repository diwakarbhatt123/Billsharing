package com.flipkart.billsharing.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BillMemberDetails {
    private String userId;
    private int amountDue;
}
