package diy.deepcode.deepcode.dto;

import java.time.Instant;

public record WaitlistEntryDto(
        Long id,
        String name,
        String email,
        String phoneNumber,
        String company,
        String role,
        Instant joinedAt
) {}