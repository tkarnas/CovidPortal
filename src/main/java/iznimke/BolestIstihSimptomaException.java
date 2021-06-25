package iznimke;

/**
 * Sluzi za hvatanje iznimaka za bolesti s istim simptomima - neoznacena iznimka
 *
 * @author Tomislav Karnas
 */

public class BolestIstihSimptomaException extends RuntimeException{

    public BolestIstihSimptomaException(String message) {
        super(message);
    }

    public BolestIstihSimptomaException(String message, Throwable cause) {
        super(message, cause);
    }

    public BolestIstihSimptomaException(Throwable cause) {
        super(cause);
    }
}
