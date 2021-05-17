package com.flipkart.billsharing.dao;

import com.flipkart.billsharing.dao.entities.Group;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@ToString
public class GroupDao {

    private Map<String, Group> groupMap = new HashMap<>();

    public String createGroup(Group group){
        groupMap.put(group.getId(),group);
        return group.getId();
    }

    public Group getGroup(String id){
        return groupMap.get(id);
    }

    public void addUserGroupBalance(String groupId, String userId, int amount){
        Group group = groupMap.get(groupId);
        int currentAmount = Optional.ofNullable(group.getUserGroupBalance().get(userId)).orElse(0);
        group.getUserGroupBalance().put(userId,currentAmount+amount);
    }

}
