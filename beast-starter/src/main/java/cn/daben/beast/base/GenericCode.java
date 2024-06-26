package cn.daben.beast.base;


/**
 * @author Justubborn
 * @since 2022/8/27
 */
public enum GenericCode implements ErrorCode {
    SUCCESS(0, "请求成功"),
    FAILURE(-1, "系统繁忙,请稍后重试"),
    LIMITER(-2, "请求过于频繁"),
    INVALID_TOKEN(100100,"无效的令牌"),
    NO_PERMISSION(100101,"没有权限");
  
    
    private final int errcode;

    private final String errmsg;

    GenericCode(int code, String msg) {
        this.errcode = code;
        this.errmsg = msg;
        ErrorManger.register(this);
    }


    @Override
    public Integer getValue() {
        return errcode;
    }

    @Override
    public String getDescription() {
        return errmsg;
    }

}
