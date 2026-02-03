package csv;

public abstract class InvalidTestCase<T extends Throwable> {
    public final String input;
    public final T exceptionInstance;
    public final Class<T> exceptionType;

    protected InvalidTestCase(String input, T exceptionInstance, Class<T> exceptionType) {
        this.input = input;
        this.exceptionInstance = exceptionInstance;
        this.exceptionType = exceptionType;
    }

    public abstract boolean isSameException(Throwable exceptionInstance);
}
