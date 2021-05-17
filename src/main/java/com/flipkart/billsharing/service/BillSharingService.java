package com.flipkart.billsharing.service;

import com.flipkart.billsharing.common.BillStatus;
import com.flipkart.billsharing.common.MemberBillStatus;
import com.flipkart.billsharing.common.SplitMethod;
import com.flipkart.billsharing.dao.BillDao;
import com.flipkart.billsharing.dao.GroupDao;
import com.flipkart.billsharing.dao.UserDao;
import com.flipkart.billsharing.dao.entities.Bill;
import com.flipkart.billsharing.dao.entities.Group;
import com.flipkart.billsharing.dao.entities.MemberBillDetails;
import com.flipkart.billsharing.dao.entities.User;
import com.flipkart.billsharing.service.dto.BillMemberDetails;
import com.flipkart.billsharing.service.dto.CreateBillDTO;
import com.flipkart.billsharing.service.dto.CreateGroupDTO;
import com.flipkart.billsharing.service.vo.CreateBillVO;
import com.flipkart.billsharing.service.vo.CreateGroupVO;
import com.flipkart.billsharing.service.vo.UserDashboardVO;

import java.util.*;
import java.util.stream.Collectors;

public class BillSharingService implements IBillSharingService{

    private BillDao billDao;
    private GroupDao groupDao;
    public UserDao userDao;

    private BillSharingService(){
        this.billDao = new BillDao();
        this.groupDao = new GroupDao();
        this.userDao = new UserDao();
    }

    @Override
    public CreateGroupVO createGroup(CreateGroupDTO createGroupDTO) throws Exception {
        Group group = convertDTOToEntity(createGroupDTO);
        String groupID = groupDao.createGroup(group);
        addMembersToGroup(createGroupDTO.getMemberIds(), group);
        return CreateGroupVO.builder().groupId(groupID).build();
    }

    @Override
    public CreateBillVO createBill(CreateBillDTO createBillDTO) throws Exception {
        Bill bill = convertDTOToEntity(createBillDTO);
        String billId = billDao.createBill(bill);
        updateUserTotalBalance(bill);
        return CreateBillVO.builder().billId(billId).build();
    }

    @Override
    public UserDashboardVO getUserDashBoard(String userId) throws Exception {
        User user = userDao.getUser(userId);
        return UserDashboardVO.builder()
                .totalBalance(user.getTotalBalance())
                .build();
    }

    private void addMembersToGroup(List<String> memberIds, Group group) {
        memberIds.forEach(memberId-> userDao.addUserToGroup(memberId,group));
    }


    private void updateUserTotalBalance(Bill bill) {
        bill.getMemberBillDetails().forEach(billMember-> {
            userDao.addToTotalBalance(billMember.getUser().getId(),billMember.getTotalAmountDue());
            userDao.addUserBalanceMapping(bill.getPaidBy().getId(),billMember.getUser().getId(),billMember.getTotalAmountDue());
            groupDao.addUserGroupBalance(bill.getGroup().getId(),billMember.getUser().getId(),billMember.getTotalAmountDue());
        });
    }

    private Bill convertDTOToEntity(CreateBillDTO createBillDTO) throws Exception {

        return Bill.builder()
                .id(UUID.randomUUID().toString())
                .paidBy(userDao.getUser(createBillDTO.getPaidByUserId()))
                .splitMethod(createBillDTO.getSplitMethod())
                .status(BillStatus.CREATED)
                .group(groupDao.getGroup(createBillDTO.getGroupId()))
                .totalAmount(createBillDTO.getTotalAmount())
                .memberBillDetails(getBillMemberDetails(createBillDTO.getBillMembers(),createBillDTO.getSplitMethod(), createBillDTO.getTotalAmount()))
                .build();
    }

    private List<MemberBillDetails> getBillMemberDetails(List<BillMemberDetails> billMembers, SplitMethod splitMethod, int totalAmount) throws Exception {
        switch (splitMethod) {
            case PROPORTIONAL:
                int perPersonAmount = totalAmount/(billMembers.size()+1);
                int remainingPerPersonAmount = (totalAmount - perPersonAmount)/billMembers.size();
                return billMembers.stream().map(billMemberDetails -> MemberBillDetails.builder()
                        .memberBillStatus(MemberBillStatus.NOT_SETTLED)
                        .totalAmountDue(remainingPerPersonAmount)
                        .user(userDao.getUser(billMemberDetails.getUserId())).build()).collect(Collectors.toList());
            case NON_PROPORTIONAL:
                return billMembers.stream().map(billMemberDetails -> MemberBillDetails.builder()
                        .memberBillStatus(MemberBillStatus.NOT_SETTLED)
                        .totalAmountDue(billMemberDetails.getAmountDue())
                        .user(userDao.getUser(billMemberDetails.getUserId())).build()).collect(Collectors.toList());
        }
        throw new Exception("Invalid SplitMethod");
    }

    private Group convertDTOToEntity(CreateGroupDTO createGroupDTO) throws Exception {
        validateRequest(createGroupDTO);

        List<User> members = createGroupDTO.getMemberIds().stream().map(memberId-> userDao.getUser(memberId)).collect(Collectors.toList());

        Group group = Group.builder()
                .id(UUID.randomUUID().toString())
                .createdBy(userDao.getUser(createGroupDTO.getCreatorId()))
                .members(members)
                .groupBills(new ArrayList<>())
                .userGroupBalance(new HashMap<>())
                .build();
        return group;
    }

    private void validateRequest(CreateGroupDTO createGroupDTO) throws Exception {
        if(Objects.isNull(userDao.getUser(createGroupDTO.getCreatorId()))){
            throw new Exception("Invalid User");
        }
        if(createGroupDTO.getMemberIds().isEmpty()){
            throw new Exception("Invalid number of members");
        }
    }


    public static void main(String[] args) throws Exception {

        BillSharingService billSharingService = new BillSharingService();



        User user1 = new User();
        user1.setId(UUID.randomUUID().toString());
        user1.setName("Diwakar Bhatt");

        User user2 = new User();
        user2.setId(UUID.randomUUID().toString());
        user2.setName("Akash");

        User user3 = new User();
        user3.setId(UUID.randomUUID().toString());
        user3.setName("Nishant");

        System.out.println(billSharingService.userDao.createUser(user1));
        System.out.println(billSharingService.userDao.createUser(user2));
        System.out.println(billSharingService.userDao.createUser(user3));

        CreateGroupDTO createGroupDTO = CreateGroupDTO.builder()
                .creatorId(user1.getId())
                .memberIds(Arrays.asList(user2.getId(),user3.getId()))
                .name("Random Group")
                .build();

        CreateGroupVO createGroupVO = billSharingService.createGroup(createGroupDTO);
        System.out.println(createGroupVO);

        CreateBillDTO createBillDTO = CreateBillDTO.builder()
                .groupId(createGroupVO.getGroupId())
                .totalAmount(1500)
                .splitMethod(SplitMethod.PROPORTIONAL)
                .paidByUserId(user1.getId())
                .billMembers(Arrays.asList(BillMemberDetails.builder().userId(user3.getId()).build()))
                .build();

        CreateBillVO createBillVO = billSharingService.createBill(createBillDTO);

        System.out.println(createBillVO);
    }


}
