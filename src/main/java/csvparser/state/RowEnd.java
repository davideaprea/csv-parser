package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;
import csvparser.structure.CSVRow;

public class RowEnd extends ParsingState {
    protected RowEnd(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        throw new UnexpectedCharacterException(character, "The row is closed");
    }

    @Override
    public CSVRow end() {
        return context.rowBuilder().build();
    }
}
