package Exceptii;

public class InvalidEntityException extends RuntimeException implements RepoException {
    public InvalidEntityException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
