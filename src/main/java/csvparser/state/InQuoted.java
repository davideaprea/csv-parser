package csvparser.state;

public class InQuoted extends ParsingState {
    public InQuoted(ParsingContext context) {
        super(context);
    }

    @Override
    public void eval(char character) {
        if (character == '"') {
            context.changeState(new Escaping(context));
        } else {
            context.addCharacter(character);
        }
    }
}
