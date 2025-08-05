package com.ziniosedge.recruitment.entity;

import com.ziniosedge.recruitment.entity.ENUM.InviteStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Invite {
    @Id
    @GeneratedValue
    private Long id;

    private String candidateEmail;

    @ManyToOne
    private User recruiter;

    private String inviteLink;

    @Enumerated(EnumType.STRING)
    private InviteStatus inviteStatus;

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

