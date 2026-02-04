package csv.testcase.invalid;

public abstract class InvalidTestCase<T extends Throwable> {
    public final String input;
    public final T exceptionInstance;
    public final Class<T> exceptionType;

    public InvalidTestCase(String input, T exceptionInstance, Class<T> exceptionType) {
        this.input = input;
        this.exceptionInstance = exceptionInstance;
        this.exceptionType = exceptionType;
    }

    public abstract boolean isSameException(Throwable exceptionInstance);
}
