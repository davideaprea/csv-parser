package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class CarriageReturn implements ParsingState {
    @Override
    public void next(char character, ParsingContext parsingContext) {
        if (character == '\n') {
            parsingContext.columnBuilder.build();
            parsingContext.gridBuilder.addRow();
            parsingContext.setParsingState(new ColumnStart());
        } else {
            throw new UnexpectedCharacterException(character, "Expected LF character.");
        }
    }
}
