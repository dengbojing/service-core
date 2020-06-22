package com.yichen.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dengbojing
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException {
    private Integer code = 500;

    /**
     * Constructs a new runtime exception with the specified detail message with out fillstacktrace.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BusinessException(String message) {
        super(message,null,false,false);
    }

    public BusinessException(String message, Throwable throwable){
        super(message,throwable);
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @param code 代码
     */
    public BusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
