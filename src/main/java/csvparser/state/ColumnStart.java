package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

import java.util.ArrayList;

public class ColumnStart extends ParsingState {
    public ColumnStart(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(final char character) {
        if(context.grid().isEmpty()) {
            context.grid().add(new ArrayList<>());
        }

        if (character == '"') {
            resetColumn();
            
            return new InQuoted(context);
        }
        if (character == context.separator().symbol) {
            final String column = endColumn();
            addColumn(column);

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
}
