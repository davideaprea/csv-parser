package csvparser.state;

class InQuoted extends ParsingState {
    public InQuoted(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == '"') {
            return new Escaping(context);
        }

        context.stringBuilder().append(character);

        return this;
    }
}
