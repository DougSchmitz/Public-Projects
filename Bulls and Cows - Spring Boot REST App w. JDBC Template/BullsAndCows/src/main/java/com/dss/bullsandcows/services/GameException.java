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
public class GameException extends Exception {

    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
