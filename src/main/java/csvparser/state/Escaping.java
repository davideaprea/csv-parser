package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class Escaping implements ParsingState {
    @Override
    public void next(char character, CSVParser csvParser) {
        if (character == csvParser.separator.symbol) {
            csvParser.buildColumn();
        } else if (Character.isWhitespace(character)) {
            csvParser.setParsingState(new OutQuoted());
        } else if (character == '\r') {
            csvParser.setParsingState(new CarriageReturn());
        } else if (character == '"') {
            csvParser.addCharacter(character);
            csvParser.setParsingState(new InQuoted());
        } else {
            throw new UnexpectedCharacterException(character, "Found invalid character for escaping.");
        }
    }
}
