package exception;

public class EmailFormatException extends Exception {

    public EmailFormatException() {
        super("El formato del email no es correcto.");
    }
}
