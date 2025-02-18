package Exceptii;

public class EntityAlreadyExistsException extends RuntimeException implements RepoException {
    private final int id;

    public EntityAlreadyExistsException(int id) {
        super("Entitatea cu ID-ul " + id + " există deja.");
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Entitatea cu ID-ul " + id + " există deja.";
    }
}
