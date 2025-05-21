package diy.deepcode.deepcode.service;


import java.util.List;
import java.util.stream.Collectors;

import diy.deepcode.deepcode.dto.NewWaitlistRequest;
import diy.deepcode.deepcode.dto.WaitlistEntryDto;
import diy.deepcode.deepcode.exception.DuplicateEmailException;
import diy.deepcode.deepcode.exception.DuplicatePhoneException;
import diy.deepcode.deepcode.exception.NotFoundException;
import diy.deepcode.deepcode.model.WaitlistEntry;
import diy.deepcode.deepcode.repo.WaitlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WaitlistService {
    private static final Logger log = LoggerFactory.getLogger(WaitlistService.class);
    private final WaitlistRepository repo;

    public WaitlistService(WaitlistRepository repo) {
        this.repo = repo;
    }

    public WaitlistEntryDto add(NewWaitlistRequest req) {
        if (repo.existsByEmail(req.email())) {
            throw new DuplicateEmailException(req.email());
        }
        if (repo.existsByPhoneNumber(req.phoneNumber())) {
            throw new DuplicatePhoneException(req.phoneNumber());
        }

        var entry = new WaitlistEntry();
        entry.setName(req.name());
        entry.setEmail(req.email());
        entry.setPhoneNumber(req.phoneNumber());
        entry.setCompany(req.company());
        entry.setRole(req.role());

        var saved = repo.save(entry);
        log.info("Added to waitlist id={} email={} phone={}",
                saved.getId(), saved.getEmail(), saved.getPhoneNumber());

        return new WaitlistEntryDto(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getPhoneNumber(),
                saved.getCompany(),
                saved.getRole(),
                saved.getJoinedAt()
        );
    }

    public List<WaitlistEntryDto> getAllWaitlistEntries() {
        return repo.findAll().stream()
                .map(e -> new WaitlistEntryDto(
                        e.getId(),
                        e.getName(),
                        e.getEmail(),
                        e.getPhoneNumber(),
                        e.getCompany(),
                        e.getRole(),
                        e.getJoinedAt()))
                .collect(Collectors.toList());
    }

    public void remove(Long id) {
        repo.findById(id).ifPresentOrElse(
                e -> {
                    repo.delete(e);
                    log.info("Removed waitlist entry id={}", id);
                },
                () -> { throw new NotFoundException("Entry", id); }
        );
    }
}
