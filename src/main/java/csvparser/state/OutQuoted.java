package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class OutQuoted extends ParsingState {
    public OutQuoted(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == context.separator().symbol) {
            buildColumn();

            return new ColumnStart(context);
        }
        if (character == '\r') {
            return new CarriageReturn(context);
        }
        if (Character.isWhitespace(character)) {
            return this;
        }

        throw new UnexpectedCharacterException(character, "No values allowed after closed quoted field.");
    }
}
