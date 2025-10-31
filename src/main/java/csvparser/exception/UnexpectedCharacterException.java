package csvparser.exception;

public class UnexpectedCharacterException extends RuntimeException {
    public final char unexpectedCharacter;
    public final String cause;

    public UnexpectedCharacterException(char unexpectedCharacter, String cause) {
        super("Unexpected character " + unexpectedCharacter + "; Cause: " + cause);
        this.unexpectedCharacter = unexpectedCharacter;
        this.cause = cause;
    }
}
