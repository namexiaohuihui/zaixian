package toolskit.incomprehension;

public class CustomError extends Exception {
    private static final long serialVersionUID = 1L;

    private Integer code;

    public CustomError(Integer code) {
        super("errorCode:" + code );
        this.code = code;
    }

    public CustomError(Integer code, String msg) {
        super("errorCode:" + code + " errorMsg" + msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
