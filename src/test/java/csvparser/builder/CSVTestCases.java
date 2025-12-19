package csvparser.builder;

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
                    List.of(
                            List.of(""),
                            List.of()
                    )
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
}
