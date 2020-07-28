/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.movielibrary.ui;

import java.time.LocalDate;

/**
 *
 * @author Schmi
 */
public interface UserIO {


    public void print(String msg);

    public int readInt(String msgPrompt);

    public int readInt(String msgPrompt, int min, int max);

    public String readString(String msgPrompt);
    
    public String editString( String prompt, String originalValue );

    public int editNumber( String prompt, Integer originalValue );
    
    public LocalDate readDate(String prompt);

    public LocalDate editDate(String prompt, LocalDate originalValue);
    
}
