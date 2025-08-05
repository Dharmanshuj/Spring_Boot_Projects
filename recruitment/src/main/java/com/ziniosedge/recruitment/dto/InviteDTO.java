package com.ziniosedge.recruitment.dto;

import com.ziniosedge.recruitment.entity.ENUM.InviteStatus;
import lombok.Data;

@Data
public class InviteDTO {
    private String candidateEmail;
    private Long recruiterId;
    private String inviteLink;
    private InviteStatus inviteStatus;
}

