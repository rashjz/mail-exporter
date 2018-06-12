package rashjz.info.app.mail.mailexporter.exceptions;

import rashjz.info.app.mail.mailexporter.utils.ErrorCode;

public class WrongEmailException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final ErrorCode code;

    public WrongEmailException(ErrorCode code) {
        super();
        this.code = code;
    }

    public WrongEmailException(String message, Throwable cause, ErrorCode code) {
        super(message, cause);
        this.code = code;
    }

    public WrongEmailException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public WrongEmailException(Throwable cause, ErrorCode code) {
        super(cause);
        this.code = code;
    }

    public ErrorCode getCode() {
        return this.code;
    }
}
