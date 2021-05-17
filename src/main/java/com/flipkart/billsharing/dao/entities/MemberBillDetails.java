package com.flipkart.billsharing.dao.entities;

import com.flipkart.billsharing.common.MemberBillStatus;
import lombok.*;

import javax.xml.ws.BindingType;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberBillDetails {
    private String id;
    private User user;
    private int totalAmountDue;
    private MemberBillStatus memberBillStatus;
}
