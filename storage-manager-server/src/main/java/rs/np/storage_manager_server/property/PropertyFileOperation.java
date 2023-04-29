//package property;
package rs.np.storage_manager_server.property;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
/**
 *
 * @author Milan
 */
public class PropertyFileOperation {
    
    public static List<String> readDataFromPropertyFile(String fileName) throws Exception{
        List<String> loginCredentials = new ArrayList<>();
        Properties properties = new Properties();
        properties.load(new FileInputStream(fileName));
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        
        loginCredentials.add(url);
        loginCredentials.add(username);
        loginCredentials.add(password);
        
        return loginCredentials;
    }
    public static void writeDataToPropertyFile(List<String> parameters, String fileName) throws Exception{
        if(parameters == null || parameters.size()!= 3){
            throw new Exception("There should only be 3 list parameters given.");
        }
        Properties properties = new Properties();
        properties.load(new FileInputStream(fileName));
        properties.setProperty("url", parameters.get(0));
        properties.setProperty("username", parameters.get(1));
        properties.setProperty("password", parameters.get(2));
        properties.store(new FileOutputStream("config/dbconfig.properties"), "url configuration updated");
    }
}
