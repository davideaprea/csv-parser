package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class ColumnStart implements ParsingState {
    @Override
    public void next(final char character, final ParsingContext parsingContext) {
        if (character == '"') {
            parsingContext.columnBuilder.reset();

            parsingContext.setParsingState(new InQuoted());
        } else if (character == parsingContext.separator.symbol) {
            parsingContext.columnBuilder.build();
            parsingContext.columnBuilder.reset();

            parsingContext.setParsingState(this);
        } else if (character == '\r') {
            parsingContext.setParsingState(new CarriageReturn());
        } else if (character == '\n') {
            throw new UnexpectedCharacterException(character, "LF characters should only appear in quoted fields or after a CR character.");
        } else if (Character.isWhitespace(character)) {
            parsingContext.setParsingState(this);
            parsingContext.columnBuilder.addCharacter(character);
        } else {
            parsingContext.setParsingState(new InNormal());
            parsingContext.columnBuilder.addCharacter(character);
        }
    }
}
