package csvparser.state;

interface ParsingState {
    void next(final char character, final CSVParser csvParser);
}
