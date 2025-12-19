package csvparser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.state.*;

import java.util.List;

public class CSVParser {
    private CSVParser() {
    }

    public static List<List<String>> parse(
            final String value,
            final CSVColumnSeparator separator
    ) {
        CharacterEvaluator characterEvaluator = new CharacterEvaluator(separator);

        for (int i = 0; i < value.length(); i++) {
            characterEvaluator.eval(value.charAt(i));
        }

        return characterEvaluator.end();
    }
}
