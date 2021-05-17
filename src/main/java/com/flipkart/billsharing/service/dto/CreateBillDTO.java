package com.flipkart.billsharing.service.dto;

import com.flipkart.billsharing.common.SplitMethod;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateBillDTO {
    private String paidByUserId;
    private int totalAmount;
    private List<BillMemberDetails> billMembers;
    private SplitMethod splitMethod;
    private String groupId;
}
