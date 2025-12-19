package csvparser.builder;

import csvparser.exception.MalformedFileException;

import java.util.ArrayList;
import java.util.List;

public class GridBuilder {
    private List<List<String>> grid = new ArrayList<>();
    private StringBuilder stringBuilder = new StringBuilder();

    public void nextRow() {
        final int gridSize = grid.size();

        if (
                gridSize > 1 &&
                grid.get(gridSize - 1).size() != grid.get(gridSize - 2).size()
        ) {
            throw new MalformedFileException("Rows should all have the same number of columns.");
        }

        grid.add(new ArrayList<>());
    }

    public void appendToColumn(final char character) {
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

    public List<List<String>> build() {
        final List<List<String>> result = grid;
        grid = new ArrayList<>();
        stringBuilder = new StringBuilder();

        return result;
    }
}
