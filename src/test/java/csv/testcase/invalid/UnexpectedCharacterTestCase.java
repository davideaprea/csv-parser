package csv.testcase.invalid;

import csv.exception.UnexpectedCharacterException;

public class UnexpectedCharacterTestCase extends InvalidTestCase<UnexpectedCharacterException> {
    public UnexpectedCharacterTestCase(String input, UnexpectedCharacterException exceptionInstance, Class<UnexpectedCharacterException> exceptionType) {
        super(input, exceptionInstance, exceptionType);
    }

    @Override
    public boolean isSameException(Throwable exceptionInstance) {
        if(!(exceptionInstance instanceof UnexpectedCharacterException unexpectedCharacterException)) return false;

        return this.exceptionInstance.unexpectedCharacter == unexpectedCharacterException.unexpectedCharacter;
    }
}
