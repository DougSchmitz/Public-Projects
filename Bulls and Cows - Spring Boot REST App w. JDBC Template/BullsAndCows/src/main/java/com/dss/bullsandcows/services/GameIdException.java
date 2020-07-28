/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.bullsandcows.services;

/**
 *
 * @author Schmi
 */
public class GameIdException extends Exception {

    public GameIdException(String message) {
        super(message);
    }

    public GameIdException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
