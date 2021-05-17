package com.flipkart.billsharing.dao;

import com.flipkart.billsharing.dao.entities.Bill;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class BillDao {

    private Map<String, Bill> billMap = new HashMap<>();

    public String createBill(Bill bill){
        billMap.put(bill.getId(),bill);
        return bill.getId();
    }



}
