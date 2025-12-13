package csvparser.state;

public class Null extends ParsingState {
    public Null(ParsingContext context) {
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
