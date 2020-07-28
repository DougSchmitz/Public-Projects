/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.exceptions;

/**
 *
 * @author Schmi
 */
public class FailedUpdateException extends Exception {
    
        public FailedUpdateException(String message) {
        super(message);
    }

    public FailedUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
