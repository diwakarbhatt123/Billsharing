package com.flipkart.billsharing.dao.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {
    private String id;
    private String name;
    private User createdBy;
    private List<User> members = new ArrayList<>();
    private List<Bill> groupBills = new ArrayList<>();
    private Map<String,Integer> userGroupBalance = new HashMap<>();
}
