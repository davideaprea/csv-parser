# CSV parser

Lightweight Java library for parsing CSV files strictly compliant with the [RFC 4180](https://datatracker.ietf.org/doc/html/rfc4180) standard, designed for streaming processing of large datasets.

## Installation

### Maven

```xml
<dependency>
    <groupId>io.github.davideaprea</groupId>
    <artifactId>csv-parser</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage examples

The library provides both row-based and header-based parsing APIs:

```java
import io.github.davideaprea.csvparser.parser.Parser;

import java.io.Reader;

public class SimpleRowParsing {
    public static void main(String[] args) {
        Parser parser = new Parser(ColumnSeparator.COMMA);
        Reader reader = Files.newBufferedReader(Paths.get("data.csv"));

        parser.from(reader).forEach(row -> {
            for (String column : row) {
                System.out.println(column);
            }
        });
    }
}
```

```java
import io.github.davideaprea.csvparser.parser.Parser;

import java.io.Reader;

public class HeadedRowParsing {
    public static void main(String[] args) {
        Parser parser = new Parser(ColumnSeparator.COMMA);
        Reader reader = Files.newBufferedReader(Paths.get("data.csv"));

        parser.withHeadersFrom(reader).forEach(row -> {
            System.out.println(row.get("headerName"));
        });
    }
}
```

Invalid inputs throw exceptions, such as:

```java
import io.github.davideaprea.csvparser.exception.InvalidRowSizeException;
import io.github.davideaprea.csvparser.exception.UnexpectedCharacterException;
import io.github.davideaprea.csvparser.parser.Parser;

import java.io.Reader;

public class InvalidRowParsing {
    public static void main(String[] args) {
        Parser parser = new Parser(ColumnSeparator.COMMA);
        Reader invalidInputReader = Files.newBufferedReader(Paths.get("data.csv"));

        try {
            parser.from(invalidInputReader).forEach(row -> {
                for (String column : row) {
                    System.out.println(column);
                }
            });
        } catch (UnexpectedCharacterException e) {
            // Thrown when a character appears where it is not allowed by RFC 4180,
            // for example: a,b"c or a\n,b,c
            System.out.println("Invalid character found while parsing: " + e.getUnexpectedCharacter());
        } catch (InvalidRowSizeException e) {
            // Every row in a file must have the same number of columns
            System.out.println("Error parsing row number " + e.getRowIndex());
            System.out.println("Expected row size: " + e.getExpectedSize());
            System.out.println("Actual row size: " + e.getActualSize());
        }
    }
}
```
