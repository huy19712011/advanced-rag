package org.example.advancedrag.controller;

import lombok.RequiredArgsConstructor;
import org.example.advancedrag.audit.AuditLogEntity;
import org.example.advancedrag.audit.AuditLogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuditController {

    private final AuditLogRepository auditLogRepository;

    @GetMapping("/audit-logs")
    public List<AuditLogEntity> getLogs() {

        return auditLogRepository.findAll();
    }
}
