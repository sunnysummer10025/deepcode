package diy.deepcode.deepcode.service;

import diy.deepcode.deepcode.dto.NewWaitlistRequest;
import diy.deepcode.deepcode.dto.WaitlistEntryDto;
import diy.deepcode.deepcode.model.WaitlistEntry;
import diy.deepcode.deepcode.repo.WaitlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WaitlistServiceTest {

    @Mock
    private WaitlistRepository waitlistEntryRepository;

    @InjectMocks
    private WaitlistService waitlistService;

    private NewWaitlistRequest newWaitlistRequest;
    private WaitlistEntry waitlistEntry;

    @BeforeEach
    void setUp() {
        newWaitlistRequest = new NewWaitlistRequest("Test User","test@example.com","1234567890","Test Corp","Tester", "Test description");

        waitlistEntry = new WaitlistEntry();
        waitlistEntry.setId(1L);
        waitlistEntry.setName("Test User");
        waitlistEntry.setEmail("test@example.com");
        waitlistEntry.setPhoneNumber("1234567890");
        waitlistEntry.setCompany("Test Corp");
        waitlistEntry.setRole("Tester");
        waitlistEntry.setDescription("Test description");
    }

    @Test
    void addWaitlistEntry_shouldSaveAndReturnDto() {
        when(waitlistEntryRepository.save(any(WaitlistEntry.class))).thenAnswer(invocation -> {
            WaitlistEntry entry = invocation.getArgument(0);
            entry.setId(1L); // Simulate ID generation
            return entry;
        });

        WaitlistEntryDto resultDto = waitlistService.add(newWaitlistRequest);

        assertNotNull(resultDto);
        assertEquals(newWaitlistRequest.name(), resultDto.name());
        assertEquals(newWaitlistRequest.email(), resultDto.email());
        assertNotNull(resultDto.id());
        verify(waitlistEntryRepository, times(1)).save(any(WaitlistEntry.class));
    }

    @Test
    void getAllWaitlistEntries_shouldReturnListOfDtos() {
        when(waitlistEntryRepository.findAll()).thenReturn(Collections.singletonList(waitlistEntry));

        List<WaitlistEntryDto> resultList = waitlistService.getAllWaitlistEntries();

        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        WaitlistEntryDto resultDto = resultList.get(0);
        assertEquals(waitlistEntry.getName(), resultDto.name());
        assertEquals(waitlistEntry.getEmail(), resultDto.email());
        verify(waitlistEntryRepository, times(1)).findAll();
    }

    @Test
    void getAllWaitlistEntries_shouldReturnEmptyListWhenNoEntries() {
        when(waitlistEntryRepository.findAll()).thenReturn(Collections.emptyList());

        List<WaitlistEntryDto> resultList = waitlistService.getAllWaitlistEntries();

        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());
        verify(waitlistEntryRepository, times(1)).findAll();
    }
}
