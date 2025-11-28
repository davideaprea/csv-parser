package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

import java.util.ArrayList;

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
}
