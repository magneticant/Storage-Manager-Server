//package property;
package rs.np.storage_manager_server.property;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
/**
 * Utility klasa. Sadrzi staticke metode za rad sa property fajlovima.
 * @author Milan
 */
public class PropertyFileOperation {
    /**
     * javna staticka metoda koja iscitava i vraca podatke iz property fajla.
     * @param fileName naziv fajla (puno kvalifikovano ime sa putanjom do fajla) iz kog se citaju podaci, kao String
     * @return podaci iz fajla, kao List<String>
     * @throws Exception u slucaju da fajl nije pronadjen ili da nije moguce inicijalizovati FileInputStream.
     */
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
    /**
     * javna staticka metoda koja upisuje podatke u property fajl
     * @param parameters, kao List<String>, lista vrednosti koje treba uneti u property fajl
     * @param fileName naziv fajla (puno kvalifikovano ime sa putanjom do fajla) u koji se upisuju podaci, kao String
     * @return podaci iz fajla, kao List<String>
     * @throws Exception u slucaju da je prosledjena lista vrednosti za unos
     * null ili duzine manje ili vece od 3, fajl za unos nije pronadjen,
     *  ili nije moguce inicijalizovati FileInputStream 
     */
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
