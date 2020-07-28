/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.movielibrary.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Schmi
 */
public class UserIOConsoleImpl implements UserIO {

    final private Scanner console = new Scanner(System.in);

    /**
     *
     * A very simple method that takes in a message to display on the console
     * and then waits for a integer answer from the user to return.
     *
     * @param msg - String of information to display to the user.
     *
     */
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    /**
     *
     * A simple method that takes in a message to display on the console, and
     * then waits for an answer from the user to return.
     *
     * @param msgPrompt - String explaining what information you want from the
     * user.
     * @return the answer to the message as string
     */
    @Override
    public String readString(String msgPrompt) {
        System.out.println(msgPrompt);
        return console.nextLine();
    }

    /**
     *
     * A simple method that takes in a message to display on the console, and
     * continually reprompts the user with that message until they enter an
     * integer to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the
     * user.
     * @return the answer to the message as integer
     */
    @Override
    public int readInt(String msgPrompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                // print the message msgPrompt (ex: asking for the # of cats!)
                String stringValue = this.readString(msgPrompt);
                // Get the input line, and try and parse
                num = Integer.parseInt(stringValue); // if it's 'bob' it'll break
                invalidInput = false; // or you can use 'break;'
            } catch (NumberFormatException e) {
                // If it explodes, it'll go here and do this.
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the
     * console, and continually reprompts the user with that message until they
     * enter an integer within the specified min/max range to be returned as the
     * answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the
     * user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an integer value as an answer to the message prompt within the
     * min/max range
     */
    @Override
    public int readInt(String msgPrompt, int min, int max) {
        int result;
        do {
            result = readInt(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    @Override
    public String editString(String prompt, String originalValue) {

        String toReturn = readString(prompt);

        if (toReturn.isEmpty()) {
            toReturn = originalValue;
        }
        return toReturn;
    }

    @Override
    public int editNumber(String prompt, Integer originalValue) {
        String toReturn = readString(prompt);

        int originalValueInt = originalValue;

        if (toReturn.isEmpty()) {

            originalValueInt = originalValue;
        } else {
            originalValueInt = Integer.parseInt(toReturn);
        }
        return originalValueInt;
    }

    @Override
    public LocalDate readDate(String prompt) {
        LocalDate ld = LocalDate.now();
        boolean validInput = false;

        while (!validInput) {

            try {
                ld = LocalDate.parse(readString(prompt), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                validInput = true;
            } catch (DateTimeParseException ex) {

            }
        }
        return ld;
    }

    @Override
    public LocalDate editDate(String prompt, LocalDate originalValue) {
        LocalDate toReturn = originalValue;
        boolean validInput = false;

        while (!validInput) {
            String userInput = readString(prompt);

            if (!userInput.isEmpty()) {

                try {
                    toReturn = LocalDate.parse(userInput, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    validInput = true;
                } catch (DateTimeParseException ex) {

                }
            }

        }
        return toReturn;
    }
}
