package cn.daben.beast.exception;


/**
 * @author Justubborn
 * @since 2024/2/3
 */
public class BadRequestException extends BaseException {
    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }
}
