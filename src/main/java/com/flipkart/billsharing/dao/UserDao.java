package com.flipkart.billsharing.dao;

import com.flipkart.billsharing.dao.entities.Group;
import com.flipkart.billsharing.dao.entities.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@ToString
public class UserDao {
    private Map<String, User> userMap = new HashMap<>();

    public String createUser(User user){
        userMap.put(user.getId(),user);
        return user.getId();
    }

    public User getUser(String userId){
        return userMap.get(userId);
    }

    public void addUserToGroup(String userId, Group group){
        User memberUser = userMap.get(userId);
        memberUser.getGroupMap().put(group.getId(),group);
    }

    public void addToTotalBalance(String userId, int addAmount){
        User user = userMap.get(userId);
        user.setTotalBalance(user.getTotalBalance()+addAmount);
        userMap.put(userId,user);
    }

    public void addUserBalanceMapping(String paidByUserId, String balanceForUserId, int amount){
        User user = userMap.get(paidByUserId);
        int currentBalance = Optional.ofNullable(user.getUserBalanceMapping().get(balanceForUserId)).orElse(0);
        user.getUserBalanceMapping().put(balanceForUserId,currentBalance+amount);
    }

}
