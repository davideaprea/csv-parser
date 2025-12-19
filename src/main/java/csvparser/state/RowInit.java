package csvparser.state;

public class RowInit extends ParsingState {
    protected RowInit(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        ParsingState nextState = new ColumnStart(context);

        return nextState.eval(character);
    }

    @Override
    public void end() {
    }
}
