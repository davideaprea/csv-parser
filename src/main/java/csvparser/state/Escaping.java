package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class Escaping extends ParsingState {
    protected Escaping(GridBuilder gridBuilder) {
        super(gridBuilder);
    }

    @Override
    public ParsingState eval(char character) {
        ParsingState nextState;

        if (gridBuilder.isSeparator(character)) {
            gridBuilder.endColumn();
            nextState = new ColumnStart(gridBuilder);
        } else if (character == '\r') {
            nextState = new CarriageReturn(gridBuilder);
        } else if (character == '"') {
            gridBuilder.appendToColumn(character);
            nextState = new InQuoted(gridBuilder);
        } else {
            throw new UnexpectedCharacterException(character, "Found invalid character for escaping.");
        }

        return nextState;
    }

    @Override
    public void end() {
        gridBuilder.endColumn();
    }
}
