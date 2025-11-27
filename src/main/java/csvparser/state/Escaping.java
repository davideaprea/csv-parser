package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class Escaping implements ParsingState {
    @Override
    public void next(char character, ParsingContext parsingContext) {
        if (character == parsingContext.separator.symbol) {
            parsingContext.columnBuilder.build();

            parsingContext.setParsingState(new ColumnStart());
        } else if (Character.isWhitespace(character)) {
            parsingContext.setParsingState(new OutQuoted());
        } else if (character == '\r') {
            parsingContext.setParsingState(new CarriageReturn());
        } else if (character == '"') {
            parsingContext.columnBuilder.addCharacter(character);

            parsingContext.setParsingState(new InQuoted());
        } else {
            throw new UnexpectedCharacterException(character, "Found invalid character for escaping.");
        }
    }
}
