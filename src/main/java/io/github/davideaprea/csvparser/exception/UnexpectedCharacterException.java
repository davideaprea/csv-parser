package io.github.davideaprea.csvparser.exception;

import lombok.Getter;

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
@Getter
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
}
