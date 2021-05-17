package com.flipkart.billsharing.dao.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class User {
    private String id;
    private String name;
    private String mobileNumber;
    private String email;
    private int totalBalance;
    private Map<String,Group> groupMap = new HashMap<>();
    private Map<String,Integer> userBalanceMapping = new HashMap<>();
}
