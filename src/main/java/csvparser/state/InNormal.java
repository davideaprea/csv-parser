package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class InNormal implements ParsingState {
    @Override
    public void next(char character, ParsingContext parsingContext) {
        if (character == '"' || character == '\n') {
            throw new UnexpectedCharacterException(character, "This character can't appear in a non-quoted field.");
        } else if (character == parsingContext.separator.symbol) {
            final String column = parsingContext.columnBuilder.build();
            parsingContext.columnBuilder.reset();

            parsingContext.gridBuilder.addColumn(column);

            parsingContext.setParsingState(new ColumnStart());
        } else if (character == '\r') {
            parsingContext.setParsingState(new CarriageReturn());
        } else {
            parsingContext.columnBuilder.addCharacter(character);
        }
    }
}
