package csvparser.builder;

import csvparser.enumeration.CSVColumnSeparator;
import dto.ValidColumnParsingTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CSVColumnBuilderTest {
    @Test
    void testValidColumns() {
        List.of(
                new ValidColumnParsingTest("John", "John"),
                new ValidColumnParsingTest("John Doe", "John Doe"),
                new ValidColumnParsingTest("\"John Doe, NYC\"", "John Doe, NYC"),
                new ValidColumnParsingTest("   John Doe\t", "   John Doe\t"),
                new ValidColumnParsingTest("\",\nJohn Doe, \rNYC,\"", ",\nJohn Doe, \rNYC,"),
                new ValidColumnParsingTest("\t\"John Doe\"    ", "John Doe"),
                new ValidColumnParsingTest(",", ""),
                new ValidColumnParsingTest("    \"John \"\"Doe\"\"\"    ", "John \"Doe\"")
        ).forEach(test -> {
            final CSVColumnBuilder columnBuilder = new CSVColumnBuilder(CSVColumnSeparator.COMMA);

            for (int i = 0; i < test.input().length(); i++) {
                columnBuilder.append(test.input().charAt(i));
            }

            Assertions.assertEquals(test.expectedOutput(), columnBuilder.build());
        });
    }
}
