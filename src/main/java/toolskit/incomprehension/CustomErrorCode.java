package toolskit.incomprehension;

public enum  CustomErrorCode {
    RESOURCE_NOT_FOUND(101, "The raw data written in excel did not meet the requirements..."),

    ABNORMAL_CODE(201,"The use case ran with unexpected errors..."),

    BORING_LEISURE(301,"The raw data written in excel did not meet the requirements...") ;

    private Integer value;

    private String desc;

    private CustomErrorCode(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
