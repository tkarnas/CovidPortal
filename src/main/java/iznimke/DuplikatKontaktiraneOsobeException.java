package iznimke;

/**
 * Sluzi za hvatanje iznimaka dupliciranih kontaktiranih osoba - oznacena iznimka
 *
 * @author Tomislav Karnas
 */
public class DuplikatKontaktiraneOsobeException extends Exception{

    public DuplikatKontaktiraneOsobeException(String message) {
        super(message);
    }

    public DuplikatKontaktiraneOsobeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplikatKontaktiraneOsobeException(Throwable cause) {
        super(cause);
    }
}
