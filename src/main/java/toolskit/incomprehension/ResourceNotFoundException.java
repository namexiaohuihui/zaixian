package toolskit.incomprehension;

public class ResourceNotFoundException extends CustomError{

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {
        super(CustomErrorCode.RESOURCE_NOT_FOUND.getValue());
    }

    public ResourceNotFoundException(String msg) {
        super(CustomErrorCode.RESOURCE_NOT_FOUND.getValue(), msg);
    }

}
