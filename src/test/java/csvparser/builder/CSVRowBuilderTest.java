package csvparser.builder;

import csvparser.enumeration.CSVColumnSeparator;
import dto.ValidRowParsingTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CSVRowBuilderTest {
    @Test
    void testValidRows() {
        final CSVColumnBuilder columnBuilder = new CSVColumnBuilder(CSVColumnSeparator.COMMA);
        final CSVRowBuilder rowBuilder = new CSVRowBuilder(columnBuilder);

        List.of(
                new ValidRowParsingTest("John,Doe", List.of("John", "Doe")),
                new ValidRowParsingTest("\"John Doe, 21\",Canada", List.of("John Doe, 21", "Canada")),
                new ValidRowParsingTest("", List.of("")),
                new ValidRowParsingTest(",,", List.of("", "", ""))
        ).forEach(test -> {
            final String input = test.input();

            for(int i = 0; i < input.length(); i++) {
                rowBuilder.evaluate(input.charAt(i));
            }

            Assertions.assertEquals(test.expected(), rowBuilder.build());

            rowBuilder.reset();
        });
    }
}
