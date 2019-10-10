package com.yichen.config.exception;

import com.yichen.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author dengbojing
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class StorageFileNotFoundException extends BusinessException {

    public StorageFileNotFoundException(String message, Throwable throwable) {
        super(message,throwable);
    }

    public StorageFileNotFoundException(String message) {
        super(message);
    }

}
