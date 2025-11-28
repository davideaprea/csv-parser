package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class ColumnStart implements ParsingState {
    @Override
    public void next(final char character, final ParsingContext parsingContext) {
        if (character == '"') {
            parsingContext.resetColumn();
            parsingContext.setParsingState(new InQuoted());
        } else if (character == parsingContext.separator.symbol) {
            parsingContext.buildColumn();
        } else if (character == '\r') {
            parsingContext.setParsingState(new CarriageReturn());
        } else if (character == '\n') {
            throw new UnexpectedCharacterException(character, "LF characters should only appear in quoted fields or after a CR character.");
        } else if (Character.isWhitespace(character)) {
            parsingContext.addCharacter(character);
        } else {
            parsingContext.setParsingState(new InNormal());
            parsingContext.addCharacter(character);
        }
    }
}
