package csvparser.parser;

import csvparser.exception.UnexpectedCharacterException;

import java.util.List;

class Escaping extends ParsingState {
    public Escaping(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == context.separator().symbol) {
            buildColumn();

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

    @Override
    List<List<String>> buildGrid() {
        return List.of();
    }
}
