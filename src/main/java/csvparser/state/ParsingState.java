package csvparser.state;

public abstract class ParsingState {
    protected final ParsingContext context;

    protected ParsingState(ParsingContext context) {
        this.context = context;
    }

    public abstract ParsingState eval(final char character);
}
