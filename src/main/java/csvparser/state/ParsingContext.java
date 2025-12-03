package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.MalformedFileException;

import java.util.ArrayList;
import java.util.List;

public class ParsingContext {
    private final List<List<String>> grid;
    private final StringBuilder stringBuilder;
    private final CSVColumnSeparator separator;

    private ParsingState state;

    public ParsingContext(CSVColumnSeparator separator) {
        this.separator = separator;
        grid = new ArrayList<>();
        stringBuilder = new StringBuilder();
        state = new ColumnStart(this);
    }

    public void addRow() {
        final int gridSize = grid.size();

        if (
                gridSize > 1 &&
                grid.get(gridSize - 1).size() != grid.get(gridSize - 2).size()
        ) {
            throw new MalformedFileException("Rows should all have the same number of columns.");
        }

        grid.add(new ArrayList<>());
    }

    public void addCharacter(final char character) {
        if (grid.isEmpty()) {
            grid.add(new ArrayList<>());
        }

        stringBuilder.append(character);
    }

    public void endColumn() {
        final String column = stringBuilder.toString();

        stringBuilder.setLength(0);

        if (grid.isEmpty()) {
            grid.add(new ArrayList<>());
        }

        grid.getLast().add(column);
    }

    public boolean isSeparator(final char character) {
        return separator.symbol == character;
    }

    public void changeState(final ParsingState state) {
        if(state != null) {
            this.state = state;
        }
    }
}
