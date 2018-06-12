package rashjz.info.app.mail.mailexporter.utils;

public enum ErrorCode {
    INVALID_PORT_CONFIGURATION(101), INVALID_EMAIL(102);

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }
}
