//package main;
package rs.np.storage_manager_server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import rs.np.storage_manager_server.form.ServerForm;

/**
 * Main klasa Server projekta
 * @author Milan
 * 
 * 
 */
public class ProjektovanjeSoftveraServer {

    /**
     * Main metoda projekta. Pokrece {@link ServerForm}-u.
     * @param args opcioni argumenti komandne linije.
     * 
     */
    public static void main(String[] args) {
            new ServerForm().setVisible(true);
    }
    
}
