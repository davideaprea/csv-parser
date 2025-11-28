package csvparser.parser;

import csvparser.exception.UnexpectedCharacterException;

import java.util.List;

class ColumnStart extends ParsingState {
    public ColumnStart(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(final char character) {
        if (character == '"') {
            resetColumn();
            
            return new InQuoted(context);
        }
        if (character == context.separator().symbol) {
            buildColumn();

            return this;
        }
        if (character == '\r') {
            return new CarriageReturn(context);
        }
        if (character == '\n') {
            throw new UnexpectedCharacterException(character, "LF characters should only appear in quoted fields or after a CR character.");
        }

        context.stringBuilder().append(character);

        if (Character.isWhitespace(character)) {
            return this;
        }

        return new InNormal(context);
    }

    @Override
    List<List<String>> buildGrid() {
        return List.of();
    }
}
