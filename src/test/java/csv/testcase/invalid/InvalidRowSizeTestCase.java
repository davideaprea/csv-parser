package csv.testcase.invalid;

import csv.exception.InvalidRowSizeException;

public class InvalidRowSizeTestCase extends InvalidTestCase<InvalidRowSizeException> {
    public InvalidRowSizeTestCase(String input, InvalidRowSizeException exceptionInstance, Class<InvalidRowSizeException> exceptionType) {
        super(input, exceptionInstance, exceptionType);
    }

    @Override
    public boolean isSameException(Throwable exceptionInstance) {
        if(!(exceptionInstance instanceof InvalidRowSizeException rowSizeException)) return false;

        return rowSizeException.actualSize == this.exceptionInstance.actualSize &&
                rowSizeException.expectedSize == this.exceptionInstance.expectedSize &&
                rowSizeException.rowNumber == this.exceptionInstance.rowNumber;
    }
}
