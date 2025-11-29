package csvparser.state;

import csvparser.exception.MalformedFileException;
import csvparser.exception.UnexpectedCharacterException;

import java.util.ArrayList;
import java.util.List;

public class CarriageReturn extends ParsingState {
    public CarriageReturn(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character != '\n') {
            throw new UnexpectedCharacterException(character, "Expected LF character.");
        }

        buildColumn();

        final List<List<String>> grid = context.grid();
        final int gridSize = grid.size();

        if (
                gridSize > 1 &&
                grid.get(gridSize - 1).size() != grid.get(gridSize - 2).size()
        ) {
            throw new MalformedFileException("Rows should all have the same number of columns.");
        }

        context.grid().add(new ArrayList<>());

        return new ColumnStart(context);
    }
}
