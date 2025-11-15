package csvparser.state;

public class RowEnd extends ParsingState {
    protected RowEnd(StringBuilder stringBuilder) {
        super(stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        return this;
    }
}
