package toolskit.incomprehension;

public class GlobalExceptionHandler {

    public ExceptionResponse handleException(Exception ex) {

        if (ex instanceof CustomError) {

            CustomError apiException = (CustomError) ex;

            return ExceptionResponse.create(apiException.getCode(), apiException.getMessage());

        } else {

            return ExceptionResponse.create(000, "An unimplemented error occurred");

        }
    }


}
