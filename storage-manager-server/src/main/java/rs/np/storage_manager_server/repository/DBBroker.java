//package repository;
package rs.np.storage_manager_server.repository;

import java.io.FileInputStream;
import java.util.*;

import rs.np.storage_manager_server.property.PropertyFileOperation;

import java.sql.*;


/**
 *
 * @author Milan
 */
public class DBBroker {
    private Connection connection;
    private static DBBroker instance;

    private DBBroker() {
    }
    
    public static DBBroker getInstance(){
        if(instance == null)
            instance = new DBBroker();
        return instance;
    }
    
    public Connection establishConnection() throws Exception{
        if(connection == null || connection.isClosed()){
            List<String> loginData = PropertyFileOperation.readDataFromPropertyFile("config/dbconfig.properties");
            connection = DriverManager.getConnection(loginData.get(0),loginData.get(1),loginData.get(2));
            connection.setAutoCommit(false);
        }
        return connection;
    }
    
}
