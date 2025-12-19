package csvparser.state;

import csvparser.exception.MalformedFileException;

public class InQuoted extends ParsingState {
    protected InQuoted(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == '"') {
            return new Escaping(context);
        }

        context.gridBuilder().appendToColumn(character);

        return this;
    }

    @Override
    public void end() {
        throw new MalformedFileException("Unclosed quoted field.");
    }
}
