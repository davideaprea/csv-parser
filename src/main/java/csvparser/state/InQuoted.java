package csvparser.state;

class InQuoted implements ParsingState {
    @Override
    public void next(char character, CSVParser csvParser) {
        if (character == '"') {
            csvParser.setParsingState(new Escaping());
        } else {
            csvParser.addCharacter(character);
        }
    }
}
