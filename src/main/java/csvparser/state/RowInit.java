package csvparser.state;

public class RowInit extends ParsingState {
    public RowInit(ParsingContext context) {
        super(context);
    }

    @Override
    public void eval(char character) {
        context.changeState(new ColumnStart(context));

        context.evalCharacter(character);
    }

    @Override
    public void end() {

    }
}
