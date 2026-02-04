package csv.exception;

import java.util.Objects;

public class UnexpectedCharacterException extends RuntimeException {
    public final char unexpectedCharacter;
    public final String cause;

    public UnexpectedCharacterException(char unexpectedCharacter, String cause) {
        super("Unexpected character " + unexpectedCharacter + "; Cause: " + cause);
        this.unexpectedCharacter = unexpectedCharacter;
        this.cause = cause;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof UnexpectedCharacterException exception)) {
            return false;
        }

        return exception.unexpectedCharacter == unexpectedCharacter &&
                Objects.equals(exception.cause, cause);
    }
}
