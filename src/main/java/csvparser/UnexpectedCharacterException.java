package csvparser;

public class UnexpectedCharacterException extends RuntimeException {
    public final long position;
    public final char unexpectedCharacter;

    public UnexpectedCharacterException(long position, char unexpectedCharacter) {
        super("Unexpected character " + unexpectedCharacter + " at position " + position);
        this.position = position;
        this.unexpectedCharacter = unexpectedCharacter;
    }
}
