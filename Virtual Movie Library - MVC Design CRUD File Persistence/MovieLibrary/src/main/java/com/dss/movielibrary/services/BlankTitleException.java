/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.movielibrary.services;

/**
 *
 * @author Schmi
 */
public class BlankTitleException extends Exception {

    public BlankTitleException(String message) {
        super(message);
    }

    public BlankTitleException(String message, Throwable innerException) {
        super(message, innerException);
    }

}
