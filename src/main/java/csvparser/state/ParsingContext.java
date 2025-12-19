package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.MalformedFileException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParsingContext {
    private final CSVColumnSeparator separator;

    private ParsingState state;
    private List<List<String>> grid;
    private StringBuilder stringBuilder;

    public ParsingContext(CSVColumnSeparator separator) {
        this.separator = separator;
        grid = new ArrayList<>();
        stringBuilder = new StringBuilder();
        state = new RowInit(this);
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
        stringBuilder.append(character);
    }

    public void endColumn() {
        final String column = stringBuilder.toString();

        stringBuilder = new StringBuilder();

        if (grid.isEmpty()) {
            grid.add(new ArrayList<>());
        }

        grid.getLast().add(column);
    }

    public boolean isSeparator(final char character) {
        return separator.symbol == character;
    }

    public void changeState(final ParsingState state) {
        this.state = Objects.requireNonNull(state);
    }

    public void evalCharacter(final char character) {
        state.eval(character);
    }

    public List<List<String>> end() {
        state.end();

        final List<List<String>> result = grid;
        grid = new ArrayList<>();
        stringBuilder = new StringBuilder();

        return result;
    }
}
