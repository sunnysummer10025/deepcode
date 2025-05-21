package diy.deepcode.deepcode.controller;

import diy.deepcode.deepcode.dto.NewWaitlistRequest;
import diy.deepcode.deepcode.dto.WaitlistEntryDto;
import diy.deepcode.deepcode.service.WaitlistService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/waitlist")
public class WaitlistController {
    private final WaitlistService waitlistService;
    public WaitlistController(WaitlistService waitlistService) { this.waitlistService = waitlistService; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WaitlistEntryDto add(@Valid @RequestBody NewWaitlistRequest req) {
        return waitlistService.add(req);
    }

    @GetMapping
    public List<WaitlistEntryDto> list() {
        return waitlistService.getAllWaitlistEntries();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        waitlistService.remove(id);
    }
}
