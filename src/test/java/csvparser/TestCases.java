package csvparser;

import csvparser.exception.InvalidRowSizeException;
import csvparser.exception.UnexpectedCharacterException;

import java.util.List;

public class TestCases {
    private TestCases() {
    }

    public static final List<ValidTestCase> VALID_TEST_CASES = List.of(
            new ValidTestCase(
                    "abc",
                    List.of(List.of("abc"))
            ),
            new ValidTestCase(
                    "a,b,c",
                    List.of(List.of("a", "b", "c"))
            ),
            new ValidTestCase(
                    "a,b,c\r\n1,2,3",
                    List.of(
                            List.of("a", "b", "c"),
                            List.of("1", "2", "3")
                    )
            ),
            new ValidTestCase(
                    "a,,c",
                    List.of(List.of("a", "", "c"))
            ),
            new ValidTestCase(
                    "a,b,",
                    List.of(List.of("a", "b", ""))
            ),
            new ValidTestCase(
                    "\r\n",
                    List.of(List.of(""))
            ),
            new ValidTestCase(
                    " a , b ",
                    List.of(List.of(" a ", " b "))
            ),
            new ValidTestCase(
                    "\"a,b\",c",
                    List.of(List.of("a,b", "c"))
            ),
            new ValidTestCase(
                    "\"a\"\"b\",c",
                    List.of(List.of("a\"b", "c"))
            ),
            new ValidTestCase(
                    "\"\",x",
                    List.of(List.of("", "x"))
            ),
            new ValidTestCase(
                    "\"a\",\"b\",\"c\"",
                    List.of(List.of("a", "b", "c"))
            ),
            new ValidTestCase(
                    "\"a\nb\",x",
                    List.of(List.of("a\nb", "x"))
            ),
            new ValidTestCase(
                    "\"a\r\nb\",x",
                    List.of(List.of("a\r\nb", "x"))
            ),
            new ValidTestCase(
                    "a,\"b\r\nb\"\r\nc,d",
                    List.of(
                            List.of("a", "b\r\nb"),
                            List.of("c", "d")
                    )
            ),
            new ValidTestCase(
                    ",,,",
                    List.of(List.of("", "", "", ""))
            ),
            new ValidTestCase(
                    "\"\"",
                    List.of(List.of(""))
            ),
            new ValidTestCase(
                    "\"abc\"",
                    List.of(List.of("abc"))
            ),
            new ValidTestCase(
                    "",
                    List.of()
            )
    );

    public static List<InvalidTestCase<?>> invalidTestCases = List.of(
            new InvalidRowSizeTestCase(
                    "abc,def\r\nghi\r\nlmn,opq",
                    new InvalidRowSizeException(1, 1, 2),
                    InvalidRowSizeException.class
            ),
            new InvalidRowSizeTestCase(
                    "abc,def\r\nghi",
                    new InvalidRowSizeException(1, 1, 2),
                    InvalidRowSizeException.class
            ),
            new UnexpectedCharacterTestCase(
                    "a\nb",
                    new UnexpectedCharacterException(
                            '\n',
                            "This character can't appear in a non-quoted field."
                    ),
                    UnexpectedCharacterException.class
            ),
            new UnexpectedCharacterTestCase(
                    "\"abc\"x",
                    new UnexpectedCharacterException(
                            'x',
                            "Found invalid character for escaping."
                    ),
                    UnexpectedCharacterException.class
            ),
            new UnexpectedCharacterTestCase(
                    "a,b\r\nc\nd",
                    new UnexpectedCharacterException(
                            '\n',
                            "LF characters should only appear in quoted fields or after a CR character."
                    ),
                    UnexpectedCharacterException.class
            ),
            new UnexpectedCharacterTestCase(
                    "a,\"b\"c",
                    new UnexpectedCharacterException(
                            'c',
                            "Found invalid character for escaping."
                    ),
                    UnexpectedCharacterException.class
            ),
            new UnexpectedCharacterTestCase(
                    " \"abc\"",
                    new UnexpectedCharacterException(
                            '"',
                            "This character can't appear in a non-quoted field."
                    ),
                    UnexpectedCharacterException.class
            ),
            new UnexpectedCharacterTestCase(
                    "a, \"b\"",
                    new UnexpectedCharacterException(
                            '"',
                            "This character can't appear in a non-quoted field."
                    ),
                    UnexpectedCharacterException.class
            ),
            new UnexpectedCharacterTestCase(
                    "a,b\rx",
                    new UnexpectedCharacterException(
                            'x',
                            "Expected LF character."
                    ),
                    UnexpectedCharacterException.class
            ),
            new UnexpectedCharacterTestCase(
                    "a\"bc",
                    new UnexpectedCharacterException(
                            '"',
                            "This character can't appear in a non-quoted field."
                    ),
                    UnexpectedCharacterException.class
            ),
            new UnexpectedCharacterTestCase(
                    "a,b\r\nc,d\r\n1,2\n3",
                    new UnexpectedCharacterException(
                            '\n',
                            "LF characters should only appear in quoted fields or after a CR character."
                    ),
                    UnexpectedCharacterException.class
            ),
            new UnexpectedCharacterTestCase(
                    "abc\r\ndef\r",
                    new UnexpectedCharacterException(
                            '\0',
                            "Expected LF character"
                    ),
                    UnexpectedCharacterException.class
            ),
            new UnexpectedCharacterTestCase(
                    "abc\r\n\"def",
                    new UnexpectedCharacterException(
                            '\0',
                            "Unclosed quoted field."
                    ),
                    UnexpectedCharacterException.class
            ),
            new UnexpectedCharacterTestCase(
                    "\"",
                    new UnexpectedCharacterException(
                            '\0',
                            "Unclosed quoted field."
                    ),
                    UnexpectedCharacterException.class
            ),
            new UnexpectedCharacterTestCase(
                    "\"abc",
                    new UnexpectedCharacterException(
                            '\0',
                            "Unclosed quoted field."
                    ),
                    UnexpectedCharacterException.class
            ),
            new UnexpectedCharacterTestCase(
                    "\"a\"\"b",
                    new UnexpectedCharacterException(
                            '\0',
                            "Unclosed quoted field."
                    ),
                    UnexpectedCharacterException.class
            )
    );
}
