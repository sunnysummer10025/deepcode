package diy.deepcode.deepcode.exception;

public class DuplicatePhoneException extends RuntimeException {
    public DuplicatePhoneException(String phone) {
        super("Phone number already on waitlist: " + phone);
    }
}
