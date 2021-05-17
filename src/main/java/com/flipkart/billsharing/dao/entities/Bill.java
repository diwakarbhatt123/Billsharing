package com.flipkart.billsharing.dao.entities;

import com.flipkart.billsharing.common.BillStatus;
import com.flipkart.billsharing.common.SplitMethod;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bill {
    private String id;
    private Group group;
    private User paidBy;
    private int totalAmount;
    private SplitMethod splitMethod;
    private BillStatus status;
    private List<MemberBillDetails> memberBillDetails = new ArrayList<>();
}
