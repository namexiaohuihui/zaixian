package toolskit.incomprehension;

public class BoringLeisureError extends CustomError{
    private static final long serialVersionUID = 3L;

    public BoringLeisureError(String msg) {
        super(CustomErrorCode.BORING_LEISURE.getValue(), msg);
    }

}