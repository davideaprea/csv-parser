package csvparser.builder;

import csvparser.exception.InvalidRowSizeException;

import java.util.ArrayList;
import java.util.List;

public class GridBuilder {
    private List<List<String>> grid = new ArrayList<>();
    private StringBuilder currentColumn = new StringBuilder();
    private List<String> currentRow = new ArrayList<>();

    public void endRow() {
        if (!grid.isEmpty()) {
            final int currRowSize = currentRow.size();
            final int prevRowSize = grid.getLast().size();

            if(currRowSize != prevRowSize) {
                throw new InvalidRowSizeException(grid.size(), currRowSize, prevRowSize);
            }
        }

        grid.add(currentRow);

        currentRow = new ArrayList<>();
    }

    public void appendToColumn(final char character) {
        currentColumn.append(character);
    }

    public void endColumn() {
        currentRow.add(currentColumn.toString());

        currentColumn = new StringBuilder();
    }

    public List<List<String>> build() {
        final List<List<String>> result = grid;
        grid = new ArrayList<>();
        currentColumn = new StringBuilder();
        currentRow = new ArrayList<>();

        return result;
    }
}
