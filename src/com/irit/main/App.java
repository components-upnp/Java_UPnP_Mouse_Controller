package com.irit.main;

import com.irit.upnp.MouseControllerServer;

/**
 * Created by mkostiuk on 12/06/2017.
 */
public class App {

    public static void main(String[] args) {
        new Thread(new MouseControllerServer()).run();
    }

}
