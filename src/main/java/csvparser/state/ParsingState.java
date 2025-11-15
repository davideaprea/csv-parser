package csvparser.state;

public abstract class ParsingState {
    protected final StringBuilder stringBuilder;

    protected ParsingState(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public abstract ParsingState evalCharacter(final char character);
}
