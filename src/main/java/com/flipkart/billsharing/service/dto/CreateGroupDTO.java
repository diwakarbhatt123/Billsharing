package com.flipkart.billsharing.service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateGroupDTO {
    private String name;
    private List<String> memberIds;
    private String creatorId;
}
