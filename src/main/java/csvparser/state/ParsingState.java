package csvparser.state;
public interface ParsingState {
    void next(final char character, final ParsingContext parsingContext);
}
