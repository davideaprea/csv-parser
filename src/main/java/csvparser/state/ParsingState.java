package csvparser.state;

public abstract class ParsingState {
    protected final GridBuilder gridBuilder;

    protected ParsingState(GridBuilder gridBuilder) {
        this.gridBuilder = gridBuilder;
    }

    public abstract ParsingState eval(final char character);

    public abstract void end();
}
