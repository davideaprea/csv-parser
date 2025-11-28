package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class CarriageReturn implements ParsingState {
    @Override
    public void next(char character, CSVParser csvParser) {
        if (character == '\n') {
            csvParser.buildColumn();
            csvParser.addRow();
        } else {
            throw new UnexpectedCharacterException(character, "Expected LF character.");
        }
    }
}
