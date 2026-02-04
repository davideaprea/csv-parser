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

    public Optional<String> getByHeaderName(String headerName) {
        return Optional
                .ofNullable(headers.get(headerName))
                .map(row::get);
    }
}
