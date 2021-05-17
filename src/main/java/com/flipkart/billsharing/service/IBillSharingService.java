package com.flipkart.billsharing.service;

import com.flipkart.billsharing.service.dto.CreateBillDTO;
import com.flipkart.billsharing.service.dto.CreateGroupDTO;
import com.flipkart.billsharing.service.vo.CreateBillVO;
import com.flipkart.billsharing.service.vo.CreateGroupVO;
import com.flipkart.billsharing.service.vo.UserDashboardVO;

public interface IBillSharingService {

    CreateGroupVO createGroup(CreateGroupDTO createGroupDTO) throws Exception;
    CreateBillVO createBill(CreateBillDTO createBillDTO) throws Exception;
    UserDashboardVO getUserDashBoard(String userId) throws Exception;
}
