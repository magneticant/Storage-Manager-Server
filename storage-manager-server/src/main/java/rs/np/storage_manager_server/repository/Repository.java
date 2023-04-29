//package repository;
package rs.np.storage_manager_server.repository;

//import domain.Product;
//import domain.WhereClauseMode;
import java.util.List;

/**
 * Javni interfejs za CRUD operacije nad bazom podataka
 * @author Milan
 */
public interface Repository<T> {
	/**
	 * Select operacija za sve domenske objekte u bazi 
	 * @param parameter tipa java generic, genericki parametar
	 * @return lista svih domenskih objekata iz baze, kao List<T>
	 * @throws Exception ako nije moguce preuzeti domenske objekte iz baze podataka
	 */
    List<T> selectAll(T parameter) throws Exception;
    /**
     * Operacija pohranjivanja podataka za domenski objekat u bazi 
     * @param parameter tipa java generic, genericki parametar
     * @throws Exception ako nije moguce sacuvati domenski objekat u bazi podataka
     */
    void insert(T parameter) throws Exception;
    /**
     * Operacija izmene (azuriranja) domenskog objekta u bazi 
     * @param parameter tipa java generic, genericki parametar
     * @throws Exception ako nije moguce izmeniti domenski objekat u bazi podataka
     */
    void update(T parameter) throws Exception;
    /**
     * Operacija brisanja za domenski objekat u bazi 
     * @param parameter tipa java generic, genericki parametar
     * @throws Exception ako nije moguce obrisati domenski objekat u bazi podataka
     */
    void delete(T parameter) throws Exception;
    /**
     * Select operacija za sve objekte u bazi (deprecated)
     * 
     * @return lista svih domenskih objekata iz baze, kao List<T>
     */
    List<T> selectAll();
}
