package diy.deepcode.deepcode.repo;

import diy.deepcode.deepcode.model.WaitlistEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitlistRepository extends JpaRepository<WaitlistEntry, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}

