package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class InNormal implements ParsingState {
    @Override
    public void next(char character, CSVParser csvParser) {
        if (character == '"' || character == '\n') {
            throw new UnexpectedCharacterException(character, "This character can't appear in a non-quoted field.");
        } else if (character == csvParser.separator.symbol) {
            csvParser.buildColumn();
        } else if (character == '\r') {
            csvParser.setParsingState(new CarriageReturn());
        } else {
            csvParser.addCharacter(character);
        }
    }
}
