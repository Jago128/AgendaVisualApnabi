package exception;

public class EmailFormatException extends Exception {

    public EmailFormatException() {
        super("El formato del email introducido no es correcto.");
    }
}
