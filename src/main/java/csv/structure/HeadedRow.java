package csv.structure;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HeadedRow extends Row {
    private final Map<String, Integer> headers;

    public HeadedRow(List<String> columns, Map<String, Integer> headers) {
        super(columns);
        this.headers = headers;
    }

    public String getByHeaderName(String headerName) {
        return Optional.ofNullable(headers.get(headerName))
                .map(this::get)
                .orElse("");
    }
}
