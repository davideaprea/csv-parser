package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class OutQuoted implements ParsingState {
    @Override
    public void next(char character, ParsingContext parsingContext) {
        if (character == parsingContext.separator.symbol) {
            final String column = parsingContext.columnBuilder.build();
            parsingContext.columnBuilder.reset();

            parsingContext.gridBuilder.addColumn(column);

            parsingContext.setParsingState(new ColumnStart());
        } else if (character == '\r') {
            parsingContext.setParsingState(new CarriageReturn());
        } else if (!Character.isWhitespace(character)) {
            throw new UnexpectedCharacterException(character, "No values allowed after closed quoted field.");
        }
    }
}
