package exception;

public class PasswordCheckFailException extends Exception {

    public PasswordCheckFailException() {
        super("Las contraseñas no coinciden. Comprueba que esten bien escritas y que coincidan.");
    }

    public PasswordCheckFailException(String msg) {
        super(msg);
    }
}
