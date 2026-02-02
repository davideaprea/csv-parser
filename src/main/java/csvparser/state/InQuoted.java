package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;
import csvparser.structure.CSVRow;

public class InQuoted extends ParsingState {
    protected InQuoted(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == '"') {
            return new Escaping(context);
        }

        context.rowBuilder().appendToColumn(character);

        return this;
    }

    @Override
    public CSVRow end() {
        throw new UnexpectedCharacterException('\0', "Unclosed quoted field.");
    }
}
