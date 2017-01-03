package com.kaishengit.exception;

/**
 * Created by wtj on 2016/12/17.
 */
public class ServiceException extends RuntimeException {
    public ServiceException(){}
    public ServiceException(String message){
        super(message);
    }
    public ServiceException(String message,Throwable throwable){
        super(message,throwable);
    }
    public ServiceException(Throwable throwable){
        super(throwable);
    }
}
