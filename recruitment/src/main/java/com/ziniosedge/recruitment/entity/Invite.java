package com.ziniosedge.recruitment.entity;

import com.ziniosedge.recruitment.entity.ENUM.InviteStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "invites")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invite {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String candidateEmail;

    @Column(nullable = false)
    private Long recruiterId;

    @Column(nullable = false)
    private String inviteLink;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InviteStatus inviteStatus;

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

