package csvparser.state;

public class InQuoted extends ParsingState {
    protected InQuoted(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == '"') {
            return new Escaping(context);
        }

        context.rowBuilder().appendToColumn(character);

        return this;
    }
}
