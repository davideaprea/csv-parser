package csvparser.state;

public class RowInit extends ParsingState {
    protected RowInit(GridBuilder gridBuilder) {
        super(gridBuilder);
    }

    @Override
    public ParsingState eval(char character) {
        ParsingState nextState = new ColumnStart(gridBuilder);

        return nextState.eval(character);
    }

    @Override
    public void end() {
    }
}
