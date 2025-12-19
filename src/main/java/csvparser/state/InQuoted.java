package csvparser.state;

import csvparser.exception.MalformedFileException;

public class InQuoted extends ParsingState {
    protected InQuoted(GridBuilder gridBuilder) {
        super(gridBuilder);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == '"') {
            return new Escaping(gridBuilder);
        }

        gridBuilder.appendToColumn(character);

        return this;
    }

    @Override
    public void end() {
        throw new MalformedFileException("Unclosed quoted field.");
    }
}
