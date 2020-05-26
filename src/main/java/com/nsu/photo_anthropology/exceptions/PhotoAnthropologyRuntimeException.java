package com.nsu.photo_anthropology.exceptions;

import java.util.logging.Logger;

public class PhotoAnthropologyRuntimeException extends RuntimeException {

    public PhotoAnthropologyRuntimeException(String message) {
        Logger log = Logger.getLogger(PhotoAnthropologyRuntimeException.class.getName());
        log.info(message);
    }
}
