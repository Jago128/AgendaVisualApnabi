package exception;

/**
 *
 * @author Jago128
 */
public class PasswordCheckFailException extends Exception {

    /**
     *
     */
    public PasswordCheckFailException() {
        super("Las contraseñas no coinciden. Comprueba que esten bien escritas y que coincidan.");
    }

    /**
     *
     * @param msg
     */
    public PasswordCheckFailException(String msg) {
        super(msg);
    }
}
