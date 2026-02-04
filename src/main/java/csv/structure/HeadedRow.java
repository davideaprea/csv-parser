package csv.structure;

import java.util.Map;
import java.util.Optional;

public class HeadedRow {
    private final Row row;
    private final Map<String, Integer> headers;

    public HeadedRow(Row row, Map<String, Integer> headers) {
        this.row = row;
        this.headers = headers;
    }

    public Row getRow() {
        return row;
    }

    public Optional<String> getByHeaderName(String headerName) {
        return Optional
                .ofNullable(headers.get(headerName))
                .map(row::get);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof HeadedRow headedRow)) return false;

        if (!row.equals(headedRow.getRow())) return false;

        return headers
                .entrySet()
                .stream()
                .allMatch(header -> {
                    Optional<String> parameterRowValue = headedRow.getByHeaderName(header.getKey());
                    Optional<String> thisRowValue = getByHeaderName(header.getKey());

                    return parameterRowValue.equals(thisRowValue);
                });
    }
}
