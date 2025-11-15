package csvparser.state;

public class InQuoted extends ParsingState {
    protected InQuoted(StringBuilder stringBuilder) {
        super(stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if(character == '"') {
            return new Escaping(stringBuilder);
        }

        stringBuilder.append(character);

        return this;
    }
}
