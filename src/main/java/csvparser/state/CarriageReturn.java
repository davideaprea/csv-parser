package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;
import csvparser.structure.CSVRow;

public class CarriageReturn extends ParsingState {
    protected CarriageReturn(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character != '\n') {
            throw new UnexpectedCharacterException(character, "Expected LF character.");
        }

        context.rowBuilder().endColumn();

        return new RowEnd(context);
    }

    @Override
    public CSVRow end() {
        throw new UnexpectedCharacterException('\0', "Expected LF character");
    }
}
