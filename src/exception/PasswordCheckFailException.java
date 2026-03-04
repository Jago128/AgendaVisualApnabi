package exception;

/**
 * Custom Exception class for any password check failures.
 *
 * @author Jago128
 */
public class PasswordCheckFailException extends Exception {

    /**
     * Exception constructor with preset message.
     */
    public PasswordCheckFailException() {
        super("Las contraseñas no coinciden. Comprueba que esten bien escritas y que coincidan.");
    }

    /**
     * Exception constructor with message parameter.
     *
     * @param msg The message the exception will send when thrown.
     */
    public PasswordCheckFailException(String msg) {
        super(msg);
    }
}
