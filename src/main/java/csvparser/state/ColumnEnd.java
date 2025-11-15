package csvparser.state;

public class ColumnEnd extends ParsingState {
    protected ColumnEnd(StringBuilder stringBuilder) {
        super(stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        return this;
    }
}
