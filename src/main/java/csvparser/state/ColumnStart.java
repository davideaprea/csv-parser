package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class ColumnStart extends ParsingState {
    protected ColumnStart(GridBuilder gridBuilder) {
        super(gridBuilder);
    }

    @Override
    public ParsingState eval(final char character) {
        ParsingState nextState = this;

        if (character == '"') {
            nextState = new InQuoted(gridBuilder);
        } else if (gridBuilder.isSeparator(character)) {
            gridBuilder.endColumn();
        } else if (character == '\r') {
            nextState = new CarriageReturn(gridBuilder);
        } else if (character == '\n') {
            throw new UnexpectedCharacterException(character, "LF characters should only appear in quoted fields or after a CR character.");
        } else {
            gridBuilder.appendToColumn(character);
            nextState = new InNormal(gridBuilder);
        }

        return nextState;
    }

    @Override
    public void end() {
        gridBuilder.endColumn();
    }
}
