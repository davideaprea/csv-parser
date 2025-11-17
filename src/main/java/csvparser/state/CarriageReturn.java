package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class CarriageReturn extends ParsingState {
    protected CarriageReturn(StringBuilder stringBuilder, CSVColumnSeparator separator) {
        super(stringBuilder, separator);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if(character == '\n') {
            return new RowEnd(stringBuilder, separator);
        }

        throw new UnexpectedCharacterException(character, "Expected LF character.");
    }
}
