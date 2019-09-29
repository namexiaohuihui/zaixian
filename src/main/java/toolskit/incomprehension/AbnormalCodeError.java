package toolskit.incomprehension;

public class AbnormalCodeError extends CustomError{
    private static final long serialVersionUID = 2L;

    public AbnormalCodeError(String msg) {
        super(CustomErrorCode.ABNORMAL_CODE.getValue(), msg);
    }

}
