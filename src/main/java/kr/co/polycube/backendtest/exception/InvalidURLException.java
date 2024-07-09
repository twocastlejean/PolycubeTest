package kr.co.polycube.backendtest.exception;

import lombok.Getter;

@Getter
public class InvalidURLException extends RuntimeException {
    public InvalidURLException() {}

    public InvalidURLException(String message) {
        super(message);
    }

    public InvalidURLException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidURLException(Throwable cause) {
        super(cause);
    }
}
