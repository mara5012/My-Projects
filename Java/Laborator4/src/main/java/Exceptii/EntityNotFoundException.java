package Exceptii;

public class EntityNotFoundException extends RuntimeException implements RepoException {
    private final int id;

    public EntityNotFoundException(int id) {
        super("Entitatea cu ID-ul " + id + " nu a fost găsită.");
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Entitatea cu ID-ul " + id + " nu a fost găsită.";
    }
}
