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
public class NoItemsException extends Exception {

    public NoItemsException(String message) {
        super(message);
    }

    public NoItemsException(String message, Throwable cause) {
        super(message, cause);
    }

}
