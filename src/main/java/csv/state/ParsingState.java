package csv.state;

import csv.structure.Row;

/**
 * Base class for all parsing states used in the row parsing state machine.
 * <p>
 * The parsing process follows the <a href="https://refactoring.guru/design-patterns/state">State</a>
 * design pattern: each concrete
 * implementation of {@code ParsingState} represents a specific phase
 * of the parsing logic and determines how incoming characters are handled.
 */
public abstract class ParsingState {
    protected final ParsingContext context;

    /**
     * Constructs a parsing state with the given context.
     *
     * @param context the shared parsing context containing mutable state
     */
    protected ParsingState(ParsingContext context) {
        this.context = context;
    }

    /**
     * Evaluates the given character according to the current state.
     *
     * @param character the character to evaluate
     * @return the next evaluated {@code ParsingState} (which may be {@code this})
     */
    public abstract ParsingState eval(final char character);

    /**
     * Finalizes the current row and builds a {@link Row}.
     * <p>
     * This method ensures that the current column is closed before
     * constructing the final row object.
     *
     * @return the parsed {@code Row}
     */
    public Row end() {
        context.rowBuilder().endColumn();

        return context.rowBuilder().build();
    }
}
