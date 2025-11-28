package csvparser.parser;

import csvparser.exception.UnexpectedCharacterException;

class OutQuoted implements ParsingState {
    @Override
    public void next(char character, CSVParser csvParser) {
        if (character == csvParser.separator.symbol) {
            csvParser.buildColumn();
        } else if (character == '\r') {
            csvParser.setParsingState(new CarriageReturn());
        } else if (!Character.isWhitespace(character)) {
            throw new UnexpectedCharacterException(character, "No values allowed after closed quoted field.");
        }
    }
}
