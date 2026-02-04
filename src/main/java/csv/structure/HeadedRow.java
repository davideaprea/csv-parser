package csv.structure;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HeadedRow extends Row {
    private final Map<String, Integer> columnIndexes;

    public HeadedRow(List<String> columns, Map<String, Integer> columnIndexes) {
        super(columns);
        this.columnIndexes = columnIndexes;
    }

    public String getByHeaderName(String headerName) {
        return Optional.ofNullable(columnIndexes.get(headerName))
                .map(this::get)
                .orElse("");
    }
}
