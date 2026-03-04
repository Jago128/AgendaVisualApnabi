package exception;

/**
 *
 * @author Jago128
 */
public class EmailFormatException extends Exception {

    /**
     *
     */
    public EmailFormatException() {
        super("El formato del email introducido no es correcto.");
    }
}
