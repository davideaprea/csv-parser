package csvparser.builder;

import csvparser.exception.InvalidRowSizeException;
import csvparser.exception.UnexpectedCharacterException;

import java.util.List;

public class CSVTestCases {
    private CSVTestCases() {
    }

    public static final List<ValidCSVTestCase> validCSVTestCases = List.of(
            new ValidCSVTestCase(
                    "abc",
                    List.of(List.of("abc"))
            ),
            new ValidCSVTestCase(
                    "a,b,c",
                    List.of(List.of("a", "b", "c"))
            ),
            new ValidCSVTestCase(
                    "a,b,c\r\n1,2,3",
                    List.of(
                            List.of("a", "b", "c"),
                            List.of("1", "2", "3")
                    )
            ),
            new ValidCSVTestCase(
                    "a,,c",
                    List.of(List.of("a", "", "c"))
            ),
            new ValidCSVTestCase(
                    "a,b,",
                    List.of(List.of("a", "b", ""))
            ),
            new ValidCSVTestCase(
                    "\r\n",
                    List.of(List.of(""))
            ),
            new ValidCSVTestCase(
                    " a , b ",
                    List.of(List.of(" a ", " b "))
            ),
            new ValidCSVTestCase(
                    "\"a,b\",c",
                    List.of(List.of("a,b", "c"))
            ),
            new ValidCSVTestCase(
                    "\"a\"\"b\",c",
                    List.of(List.of("a\"b", "c"))
            ),
            new ValidCSVTestCase(
                    "\"\",x",
                    List.of(List.of("", "x"))
            ),
            new ValidCSVTestCase(
                    "\"a\",\"b\",\"c\"",
                    List.of(List.of("a", "b", "c"))
            ),
            new ValidCSVTestCase(
                    "\"a\nb\",x",
                    List.of(List.of("a\nb", "x"))
            ),
            new ValidCSVTestCase(
                    "\"a\r\nb\",x",
                    List.of(List.of("a\r\nb", "x"))
            ),
            new ValidCSVTestCase(
                    "a,\"b\r\nb\"\r\nc,d",
                    List.of(
                            List.of("a", "b\r\nb"),
                            List.of("c", "d")
                    )
            ),
            new ValidCSVTestCase(
                    ",,,",
                    List.of(List.of("", "", "", ""))
            ),
            new ValidCSVTestCase(
                    "\"\"",
                    List.of(List.of(""))
            ),
            new ValidCSVTestCase(
                    "\"abc\"",
                    List.of(List.of("abc"))
            ),
            new ValidCSVTestCase(
                    "",
                    List.of()
            )
    );

    public static final List<InvalidCSVTestCase<InvalidRowSizeException>> invalidRowSizeCases = List.of(
            new InvalidCSVTestCase<>("abc,def\r\nghi\r\nlmn,opq", new InvalidRowSizeException(1, 1, 2)),
            new InvalidCSVTestCase<>("abc,def\r\nghi", new InvalidRowSizeException(1, 1, 2))
    );

    public static List<InvalidCSVTestCase<UnexpectedCharacterException>> unexpectedCharacterCases = List.of(
            new InvalidCSVTestCase<>(
                    "a\nb",
                    new UnexpectedCharacterException(
                            '\n',
                            "This character can't appear in a non-quoted field."
                    )
            ),
            new InvalidCSVTestCase<>(
                    "\"abc\"x",
                    new UnexpectedCharacterException(
                            'x',
                            "Found invalid character for escaping."
                    )
            ),
            new InvalidCSVTestCase<>(
                    "a,b\r\nc\nd",
                    new UnexpectedCharacterException(
                            '\n',
                            "LF characters should only appear in quoted fields or after a CR character."
                    )
            ),
            new InvalidCSVTestCase<>(
                    "a,\"b\"c",
                    new UnexpectedCharacterException(
                            'c',
                            "Found invalid character for escaping."
                    )
            ),
            new InvalidCSVTestCase<>(
                    " \"abc\"",
                    new UnexpectedCharacterException(
                            '"',
                            "This character can't appear in a non-quoted field."
                    )
            ),
            new InvalidCSVTestCase<>(
                    "a, \"b\"",
                    new UnexpectedCharacterException(
                            '"',
                            "This character can't appear in a non-quoted field."
                    )
            ),
            new InvalidCSVTestCase<>(
                    "a,b\rx",
                    new UnexpectedCharacterException(
                            'x',
                            "Expected LF character."
                    )
            ),
            new InvalidCSVTestCase<>(
                    "a\"bc",
                    new UnexpectedCharacterException(
                            '"',
                            "This character can't appear in a non-quoted field."
                    )
            ),
            new InvalidCSVTestCase<>(
                    "a,b\r\nc,d\r\n1,2\n3",
                    new UnexpectedCharacterException(
                            '\n',
                            "LF characters should only appear in quoted fields or after a CR character."
                    )
            ),
            new InvalidCSVTestCase<>(
                    "abc\r\ndef\r",
                    new UnexpectedCharacterException(
                            '\0',
                            "Expected LF character"
                    )
            ),
            new InvalidCSVTestCase<>(
                    "abc\r\n\"def",
                    new UnexpectedCharacterException(
                            '\0',
                            "Unclosed quoted field."
                    )
            ),
            new InvalidCSVTestCase<>(
                    "\"",
                    new UnexpectedCharacterException(
                            '\0',
                            "Unclosed quoted field."
                    )
            ),
            new InvalidCSVTestCase<>(
                    "\"abc",
                    new UnexpectedCharacterException(
                            '\0',
                            "Unclosed quoted field."
                    )
            ),
            new InvalidCSVTestCase<>(
                    "\"a\"\"b",
                    new UnexpectedCharacterException(
                            '\0',
                            "Unclosed quoted field."
                    )
            )
    );
}
