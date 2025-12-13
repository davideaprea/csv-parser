package csvparser.state;

public abstract class ParsingState {
    protected final ParsingContext context;

    ParsingState(ParsingContext context) {
        this.context = context;
    }

    public abstract void eval(final char character);

    public abstract void end();
}
