package diy.deepcode.deepcode.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, Long id) {
        super(entity + " not found with id=" + id);
    }
}
