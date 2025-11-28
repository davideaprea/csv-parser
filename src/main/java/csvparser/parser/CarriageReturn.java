package csvparser.parser;

import csvparser.exception.UnexpectedCharacterException;

import java.util.ArrayList;
import java.util.List;

class CarriageReturn extends ParsingState {
    public CarriageReturn(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character != '\n') {
            throw new UnexpectedCharacterException(character, "Expected LF character.");
        }

        buildColumn();

        context.grid().add(new ArrayList<>());

        return new ColumnStart(context);
    }

    @Override
    List<List<String>> buildGrid() {
        return List.of();
    }
}
