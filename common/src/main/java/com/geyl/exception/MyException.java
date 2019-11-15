package com.geyl.exception;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
　* @description:
　* @author geyl
　* @date 2018-6-5 14:28 
　*/
public class MyException extends Exception {

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, NoSuchAlgorithmException ex) {
    }

    public MyException(String message, NullPointerException npe) {
    }

    public MyException(String message, IOException ioe) {
    }
}
