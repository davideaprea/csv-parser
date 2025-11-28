package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class OutQuoted implements ParsingState {
    @Override
    public void next(char character, ParsingContext parsingContext) {
        if (character == parsingContext.separator.symbol) {
            parsingContext.buildColumn();
        } else if (character == '\r') {
            parsingContext.setParsingState(new CarriageReturn());
        } else if (!Character.isWhitespace(character)) {
            throw new UnexpectedCharacterException(character, "No values allowed after closed quoted field.");
        }
    }
}
