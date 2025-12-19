package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class InNormal extends ParsingState {
    protected InNormal(GridBuilder gridBuilder) {
        super(gridBuilder);
    }

    @Override
    public ParsingState eval(char character) {
        ParsingState nextState = this;

        if (character == '"' || character == '\n') {
            throw new UnexpectedCharacterException(character, "This character can't appear in a non-quoted field.");
        } else if (gridBuilder.isSeparator(character)) {
            gridBuilder.endColumn();
            nextState = new ColumnStart(gridBuilder);
        } else if (character == '\r') {
            nextState = new CarriageReturn(gridBuilder);
        } else {
            gridBuilder.appendToColumn(character);
        }

        return nextState;
    }

    @Override
    public void end() {
        gridBuilder.endColumn();
    }
}
