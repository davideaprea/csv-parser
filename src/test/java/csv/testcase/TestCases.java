package csv.testcase;

import csv.exception.InvalidRowSizeException;
import csv.exception.UnexpectedCharacterException;
import csv.structure.HeadedRow;
import csv.structure.Row;
import csv.testcase.invalid.InvalidTestCase;
import csv.testcase.valid.ValidHeadedRowsTestCase;
import csv.testcase.valid.ValidRowsTestCase;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

public class TestCases {
    private TestCases() {
    }

    public static final List<ValidRowsTestCase> VALID_ROWS_TEST_CASES = List.of(
            new ValidRowsTestCase(
                    new StringReader("abc"),
                    List.of(new Row(List.of("abc")))
            ),

            new ValidRowsTestCase(
                    new StringReader("a,b,c"),
                    List.of(new Row(List.of("a", "b", "c")))
            ),

            new ValidRowsTestCase(
                    new StringReader("a,b,c\r\n1,2,3"),
                    List.of(
                            new Row(List.of("a", "b", "c")),
                            new Row(List.of("1", "2", "3"))
                    )
            ),

            new ValidRowsTestCase(
                    new StringReader("a,,c"),
                    List.of(new Row(List.of("a", "", "c")))
            ),

            new ValidRowsTestCase(
                    new StringReader("a,b,"),
                    List.of(new Row(List.of("a", "b", "")))
            ),

            new ValidRowsTestCase(
                    new StringReader("\r\n"),
                    List.of(new Row(List.of("")))
            ),

            new ValidRowsTestCase(
                    new StringReader(" a , b "),
                    List.of(new Row(List.of(" a ", " b ")))
            ),

            new ValidRowsTestCase(
                    new StringReader("\"a,b\",c"),
                    List.of(new Row(List.of("a,b", "c")))
            ),

            new ValidRowsTestCase(
                    new StringReader("\"a\"\"b\",c"),
                    List.of(new Row(List.of("a\"b", "c")))
            ),

            new ValidRowsTestCase(
                    new StringReader("\"\",x"),
                    List.of(new Row(List.of("", "x")))
            ),

            new ValidRowsTestCase(
                    new StringReader("\"a\",\"b\",\"c\""),
                    List.of(new Row(List.of("a", "b", "c")))
            ),

            new ValidRowsTestCase(
                    new StringReader("\"a\nb\",x"),
                    List.of(new Row(List.of("a\nb", "x")))
            ),

            new ValidRowsTestCase(
                    new StringReader("\"a\r\nb\",x"),
                    List.of(new Row(List.of("a\r\nb", "x")))
            ),

            new ValidRowsTestCase(
                    new StringReader("a,\"b\r\nb\"\r\nc,d"),
                    List.of(
                            new Row(List.of("a", "b\r\nb")),
                            new Row(List.of("c", "d"))
                    )
            ),

            new ValidRowsTestCase(
                    new StringReader(",,,"),
                    List.of(new Row(List.of("", "", "", "")))
            ),

            new ValidRowsTestCase(
                    new StringReader("\"\""),
                    List.of(new Row(List.of("")))
            ),

            new ValidRowsTestCase(
                    new StringReader("\"abc\""),
                    List.of(new Row(List.of("abc")))
            ),

            new ValidRowsTestCase(
                    new StringReader(""),
                    List.of()
            )
    );

    public static List<InvalidTestCase> invalidTestCases = List.of(
            new InvalidTestCase(
                    "abc,def\r\nghi\r\nlmn,opq",
                    new InvalidRowSizeException(1, 1, 2)
            ),
            new InvalidTestCase(
                    "abc,def\r\nghi",
                    new InvalidRowSizeException(1, 1, 2)
            ),
            new InvalidTestCase(
                    "a\nb",
                    new UnexpectedCharacterException(
                            '\n',
                            "This character can't appear in a non-quoted field."
                    )
            ),
            new InvalidTestCase(
                    "\"abc\"x",
                    new UnexpectedCharacterException(
                            'x',
                            "Found invalid character for escaping."
                    )
            ),
            new InvalidTestCase(
                    "a,b\r\nc\nd",
                    new UnexpectedCharacterException(
                            '\n',
                            "LF characters should only appear in quoted fields or after a CR character."
                    )
            ),
            new InvalidTestCase(
                    "a,\"b\"c",
                    new UnexpectedCharacterException(
                            'c',
                            "Found invalid character for escaping."
                    )
            ),
            new InvalidTestCase(
                    " \"abc\"",
                    new UnexpectedCharacterException(
                            '"',
                            "This character can't appear in a non-quoted field."
                    )
            ),
            new InvalidTestCase(
                    "a, \"b\"",
                    new UnexpectedCharacterException(
                            '"',
                            "This character can't appear in a non-quoted field."
                    )
            ),
            new InvalidTestCase(
                    "a,b\rx",
                    new UnexpectedCharacterException(
                            'x',
                            "Expected LF character."
                    )
            ),
            new InvalidTestCase(
                    "a\"bc",
                    new UnexpectedCharacterException(
                            '"',
                            "This character can't appear in a non-quoted field."
                    )
            ),
            new InvalidTestCase(
                    "a,b\r\nc,d\r\n1,2\n3",
                    new UnexpectedCharacterException(
                            '\n',
                            "LF characters should only appear in quoted fields or after a CR character."
                    )
            ),
            new InvalidTestCase(
                    "abc\r\ndef\r",
                    new UnexpectedCharacterException(
                            '\0',
                            "Expected LF character"
                    )
            ),
            new InvalidTestCase(
                    "abc\r\n\"def",
                    new UnexpectedCharacterException(
                            '\0',
                            "Unclosed quoted field."
                    )
            ),
            new InvalidTestCase(
                    "\"",
                    new UnexpectedCharacterException(
                            '\0',
                            "Unclosed quoted field."
                    )
            ),
            new InvalidTestCase(
                    "\"abc",
                    new UnexpectedCharacterException(
                            '\0',
                            "Unclosed quoted field."
                    )
            ),
            new InvalidTestCase(
                    "\"a\"\"b",
                    new UnexpectedCharacterException(
                            '\0',
                            "Unclosed quoted field."
                    )
            )
    );

    public static final List<ValidHeadedRowsTestCase> VALID_HEADED_ROWS_TEST_CASES = List.of(
            new ValidHeadedRowsTestCase(
                    new StringReader("a,b\r\n1,2\r\n3,4"),
                    List.of(
                            new HeadedRow(
                                    new Row(List.of("1", "2")),
                                    Map.of("a", 0, "b", 1)
                            ),
                            new HeadedRow(
                                    new Row(List.of("3", "4")),
                                    Map.of("a", 0, "b", 1)
                            )
                    )
            ),
            new ValidHeadedRowsTestCase(
                    new StringReader("a,,c\r\n1,2,3"),
                    List.of(
                            new HeadedRow(
                                    new Row(List.of("1", "2", "3")),
                                    Map.of("a", 0, "", 1, "c", 2)
                            )
                    )
            ),
            new ValidHeadedRowsTestCase(
                    new StringReader("a,b,c"),
                    List.of()
            ),
            new ValidHeadedRowsTestCase(
                    new StringReader(""),
                    List.of()
            )
    );
}
