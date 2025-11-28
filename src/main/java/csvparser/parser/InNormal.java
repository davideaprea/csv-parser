package csvparser.parser;

import csvparser.exception.UnexpectedCharacterException;

import java.util.List;

class InNormal extends ParsingState {
    public InNormal(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == '"' || character == '\n') {
            throw new UnexpectedCharacterException(character, "This character can't appear in a non-quoted field.");
        }
        if (character == context.separator().symbol) {
            buildColumn();

            return new ColumnStart(context);
        }
        if (character == '\r') {
            return new CarriageReturn(context);
        }

        context.stringBuilder().append(character);

        return this;
    }

    @Override
    List<List<String>> buildGrid() {
        return List.of();
    }
}
