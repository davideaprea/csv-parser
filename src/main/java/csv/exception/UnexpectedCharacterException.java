package csv.exception;

/**
 * Exception thrown when the parser found a character
 * that violates the expected format or syntax rules.
 * <p>
 * Example inputs that may cause this exception:
 * <ul>
 *     <li>{@code a,b",c} — unmatched closing quote</li>
 *     <li>{@code a,\rb,c} — carriage return must be followed by line feed character
 *     when not allowed by the parsing rules</li>
 * </ul>
 *
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc4180#page-3">RFC 4180 standard</a>
 */
public class UnexpectedCharacterException extends RuntimeException {
    private final char unexpectedCharacter;

    /**
     * Constructs a new {@code UnexpectedCharacterException}.
     *
     * @param unexpectedCharacter the character that was encountered unexpectedly
     * @param cause               a description of the reason why the character is invalid
     */
    public UnexpectedCharacterException(char unexpectedCharacter, String cause) {
        super("Unexpected character " + unexpectedCharacter + "; Cause: " + cause);
        this.unexpectedCharacter = unexpectedCharacter;
    }

    /**
     * @return the character that was not expected in the current parsing context.
     */
    public char getUnexpectedCharacter() {
        return unexpectedCharacter;
    }

    /**
     * Compares this exception with another object for equality.
     * <p>
     * Two {@code UnexpectedCharacterException} instances are considered equal
     * if they contain the same unexpected character.
     *
     * @param obj the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof UnexpectedCharacterException exception)) {
            return false;
        }

        return exception.unexpectedCharacter == unexpectedCharacter;
    }
}
