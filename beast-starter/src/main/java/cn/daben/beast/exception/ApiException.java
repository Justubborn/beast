package cn.daben.beast.exception;

import cn.daben.beast.base.ErrorCode;
import cn.daben.beast.base.GenericCode;
import lombok.Getter;

/**
 * @author Justubborn
 * @since 2022/12/16
 */
@Getter
public class ApiException extends BaseException {
    private final Integer errcode;
    private final String errmsg;

    // 手动设置异常
    public ApiException(ErrorCode errorCode, String message) {
        super(message);
        this.errcode = errorCode.getValue();
        this.errmsg = errorCode.getDescription();
    }

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errcode = errorCode.getValue();
        this.errmsg = errorCode.getDescription();
    }

    // 默认异常使用APP_ERROR状态码
    public ApiException(String message) {
        super(message);
        this.errcode = GenericCode.FAILURE.getValue();
        this.errmsg = GenericCode.FAILURE.getDescription();
    }

}
