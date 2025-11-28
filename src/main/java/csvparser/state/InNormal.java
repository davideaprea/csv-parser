package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class InNormal implements ParsingState {
    @Override
    public void next(char character, ParsingContext parsingContext) {
        if (character == '"' || character == '\n') {
            throw new UnexpectedCharacterException(character, "This character can't appear in a non-quoted field.");
        } else if (character == parsingContext.separator.symbol) {
            parsingContext.buildColumn();
        } else if (character == '\r') {
            parsingContext.setParsingState(new CarriageReturn());
        } else {
            parsingContext.addCharacter(character);
        }
    }
}
