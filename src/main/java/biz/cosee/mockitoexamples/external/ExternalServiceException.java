package biz.cosee.mockitoexamples.external;

import java.io.IOException;

public class ExternalServiceException extends RuntimeException {

    public ExternalServiceException(String message) {
        super(message);
    }

    public ExternalServiceException(String message, IOException cause) {
        super(message, cause);
    }
}
