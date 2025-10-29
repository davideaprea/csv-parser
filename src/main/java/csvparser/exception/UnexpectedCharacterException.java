package csvparser.exception;

public class UnexpectedCharacterException extends RuntimeException {
    public long position;
    public char unexpectedCharacter;

    public UnexpectedCharacterException(long position, char unexpectedCharacter) {
        super("Unexpected character " + unexpectedCharacter + " at position " + position);
        this.position = position;
        this.unexpectedCharacter = unexpectedCharacter;
    }

    public UnexpectedCharacterException(char unexpectedCharacter) {
        super("Unexpected character " + unexpectedCharacter);
        this.unexpectedCharacter = unexpectedCharacter;
    }
}
