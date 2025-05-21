package diy.deepcode.deepcode.exception;


public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("Email already on waitlist: " + email);
    }
}

