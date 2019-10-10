package com.yichen.config.exception;

import com.yichen.exception.BusinessException;

/**
 * @author dengbojing
 */
public class FileStorageException extends BusinessException {

    public FileStorageException(String message) {
        super(message);

    }

    public FileStorageException(String message, Throwable throwable) {
        super(message,throwable);
    }
}
