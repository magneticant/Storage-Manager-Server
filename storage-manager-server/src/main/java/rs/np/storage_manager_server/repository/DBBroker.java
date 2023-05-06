//package repository;
package rs.np.storage_manager_server.repository;

import java.io.FileInputStream;
import java.util.*;

import rs.np.storage_manager_server.property.PropertyFileOperation;

import java.sql.*;


/**
 * Klasa za uspostavljanje konekcije sa bazom podataka, implementirana preko Singleton pattern-a.
 * 
 * @author Milan
 */
public class DBBroker {
	/**
	 * privatni atribut konekcija, tipa {@link Connection}
	 */
    private Connection connection;
    /**
     * privatni atribut instance, tipa {@link DBBroker}, koji sluzi u implementaciji Singleton pattern-a
     */
    private static DBBroker instance;
    /**
     * privatni neparametrizovani konstruktor, deo Singleton pattern-a
     */
    private DBBroker() {
    }
    /**
     * javna staticka metoda za dobijanje instance klase preko Singleton pattern-a
     * @return instance, tipa {@link DBBroker}
     */
    public static DBBroker getInstance(){
        if(instance == null)
            instance = new DBBroker();
        return instance;
    }
    /**
     * metoda za uspostavljanje konekcije sa bazom podataka
     * @return connection, tipa {@link Connection}
     * @throws Exception ako nije moguce procitati podatke iz konfiguracionog fajla, drajver ne postoji,
     * server za bazu podataka nije podignut ili ako konfiguracioni fajl ne postoji
     */
    public Connection establishConnection() throws Exception{
        if(connection == null || connection.isClosed()){
            List<String> loginData = PropertyFileOperation.readDataFromPropertyFile("config/dbconfig.properties");
            connection = DriverManager.getConnection(loginData.get(0),loginData.get(1),loginData.get(2));
            connection.setAutoCommit(false);
        }
        return connection;
    }
    
}
