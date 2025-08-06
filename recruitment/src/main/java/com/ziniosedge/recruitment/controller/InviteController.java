package com.ziniosedge.recruitment.controller;

import com.ziniosedge.recruitment.dto.InviteRequestDTO;
import com.ziniosedge.recruitment.service.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invites")
@RequiredArgsConstructor
public class InviteController {
    private final InviteService inviteService;

    @PostMapping("/send")
    public ResponseEntity<String> sendInvite(@RequestBody InviteRequestDTO request) {
        inviteService.sendInvite(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Invite send successfully");
    }

    @GetMapping("/respond/{status}")
    public ResponseEntity<String> respondToInvite(@PathVariable("status") String status, @RequestParam("invite_id") Long inviteId) {
        boolean updated = inviteService.updateStatus(inviteId, status);
        if(!updated) {
            return ResponseEntity.badRequest().body("Invalid invite ID or status");
        }
        return ResponseEntity.ok("Invite status updated to: " + status);
    }
}
