package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class ColumnStart implements ParsingState {
    @Override
    public void next(final char character, final CSVParser csvParser) {
        if (character == '"') {
            csvParser.resetColumn();
            csvParser.setParsingState(new InQuoted());
        } else if (character == csvParser.separator.symbol) {
            csvParser.buildColumn();
        } else if (character == '\r') {
            csvParser.setParsingState(new CarriageReturn());
        } else if (character == '\n') {
            throw new UnexpectedCharacterException(character, "LF characters should only appear in quoted fields or after a CR character.");
        } else if (Character.isWhitespace(character)) {
            csvParser.addCharacter(character);
        } else {
            csvParser.setParsingState(new InNormal());
            csvParser.addCharacter(character);
        }
    }
}
