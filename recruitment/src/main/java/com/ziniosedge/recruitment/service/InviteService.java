package com.ziniosedge.recruitment.service;

import com.ziniosedge.recruitment.dto.InviteRequestDTO;
import com.ziniosedge.recruitment.entity.ENUM.InviteStatus;
import com.ziniosedge.recruitment.entity.Invite;
import com.ziniosedge.recruitment.repository.InviteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository repository;
    private final EmailService emailService;

    public void sendInvite(InviteRequestDTO request) {
        String inviteLink = "http://localhost:5050/api/invites/respond/";
        String subject = "Interview Invitation from ZiniosEdge";
        String body = """
        Dear Candidate,
        
        We are pleased to invite you to the next step of the recruitment process at ZiniosEdge.
        
        Please click the link below to confirm your interest:
        
        👉 Respond Here: %s
        
        This link is unique to you and is valid for a limited time.
        
        If you have any questions, feel free to reach out to your recruiter.
        
        Best regards,
        ZiniosEdge
        """.formatted(inviteLink);

        emailService.sendEmail(request.getCandidateEmail(), subject, body);

        Invite invite = Invite.builder()
                .candidateEmail(request.getCandidateEmail())
                .recruiterId(request.getRecruiterId())
                .inviteLink(inviteLink)
                .inviteStatus(InviteStatus.SENT)
                .createdTime(LocalDateTime.now())
                .build();

        repository.save(invite);
    }

    public boolean updateStatus(Long inviteId, String status) {
        Optional<Invite> optionalInvite = repository.findById(inviteId);
        if(optionalInvite.isEmpty()) return false;

        Invite invite = optionalInvite.get();

        try {
            InviteStatus status1 = InviteStatus.valueOf(status.toUpperCase().trim());
            System.out.println(status1);
            invite.setInviteStatus(status1);
            invite.setUpdatedTime(LocalDateTime.now());
            repository.save(invite);
            return true;
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }
}
