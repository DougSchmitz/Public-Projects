/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.movielibrary;

import com.dss.movielibrary.controller.MovieLibraryController;
import com.dss.movielibrary.dao.MovieLibraryDao;
import com.dss.movielibrary.dao.MovieLibraryDaoFileImpl;
import com.dss.movielibrary.services.MovieLibraryService;
import com.dss.movielibrary.ui.MovieLibraryView;
import com.dss.movielibrary.ui.UserIO;
import com.dss.movielibrary.ui.UserIOConsoleImpl;

/**
 *
 * @author Schmi
 */
public class App {
    
    public static void main(String[] args) {
        
        UserIO myIo= new UserIOConsoleImpl();
        MovieLibraryView myView = new MovieLibraryView(myIo);
        MovieLibraryDao myDao = new MovieLibraryDaoFileImpl();
        MovieLibraryService myService = new MovieLibraryService (myDao);
        MovieLibraryController myController = new MovieLibraryController(myView, myService);
        
        myController.run();
    }
    
}
