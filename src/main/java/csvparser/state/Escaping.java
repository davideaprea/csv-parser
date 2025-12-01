package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class Escaping extends ParsingState {
    public Escaping(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == context.separator().symbol) {
            final String column = endColumn();
            addColumn(column);

            return new ColumnStart(context);
        }
        if (Character.isWhitespace(character)) {
            return new OutQuoted(context);
        }
        if (character == '\r') {
            return new CarriageReturn(context);
        }
        if (character == '"') {
            context.stringBuilder().append(character);

            return new InQuoted(context);
        }

        throw new UnexpectedCharacterException(character, "Found invalid character for escaping.");
    }
}
