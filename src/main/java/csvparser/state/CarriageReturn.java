package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class CarriageReturn implements ParsingState {
    @Override
    public void next(char character, ParsingContext parsingContext) {
        if (character == '\n') {
            parsingContext.buildColumn();
            parsingContext.addRow();
        } else {
            throw new UnexpectedCharacterException(character, "Expected LF character.");
        }
    }
}
