package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class Escaping implements ParsingState {
    @Override
    public void next(char character, ParsingContext parsingContext) {
        if (character == parsingContext.separator.symbol) {
            parsingContext.buildColumn();
        } else if (Character.isWhitespace(character)) {
            parsingContext.setParsingState(new OutQuoted());
        } else if (character == '\r') {
            parsingContext.setParsingState(new CarriageReturn());
        } else if (character == '"') {
            parsingContext.addCharacter(character);
            parsingContext.setParsingState(new InQuoted());
        } else {
            throw new UnexpectedCharacterException(character, "Found invalid character for escaping.");
        }
    }
}
