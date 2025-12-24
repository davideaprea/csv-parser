package csvparser.builder;

import csvparser.exception.UnexpectedCharacterException;

public class UnexpectedCharacterTestCase extends InvalidCSVTestCase<UnexpectedCharacterException> {
    protected UnexpectedCharacterTestCase(String input, UnexpectedCharacterException exceptionInstance, Class<UnexpectedCharacterException> exceptionType) {
        super(input, exceptionInstance, exceptionType);
    }

    @Override
    public boolean isSameException(Throwable exceptionInstance) {
        if(!(exceptionInstance instanceof UnexpectedCharacterException unexpectedCharacterException)) return false;

        return this.exceptionInstance.unexpectedCharacter == unexpectedCharacterException.unexpectedCharacter;
    }
}
