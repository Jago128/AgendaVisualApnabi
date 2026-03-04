package exception;

/**
 * Custom Exception class for email format check failures.
 *
 * @author Jago128
 */
public class EmailFormatException extends Exception {

    /**
     * Exception constructor with preset message.
     */
    public EmailFormatException() {
        super("El formato del email introducido no es correcto.");
    }
}
