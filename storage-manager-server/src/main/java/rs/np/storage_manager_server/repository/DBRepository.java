//package repository;
package rs.np.storage_manager_server.repository;

/**
 * Javni interfejs koji nasledjuje interfejs Repository<T> (generic), i daje default implementacije
 * metoda connect, disconnect, commit i rollback
 * @author Milan
 */
public interface DBRepository<T> extends Repository<T> {
	/**
	 * default javna metoda za povezivanje sa bazom podataka
	 * @throws Exception ako nije uspelo povezivanje sa bazom (vidi: DBBroker)
	 */
    default public void connect()throws Exception{
        DBBroker.getInstance().establishConnection();
    }
    /**
     * default javna metoda za disconnect sa bazom podataka
     * @throws Exception ukoliko nije moguce raskinuti vezu sa bazom podataka
     */
    default public void disconnect() throws Exception {
        DBBroker.getInstance().establishConnection().close();
    } 
    /**
     * default javna metoda za commit transakcije
     * @throws Exception ako nije moguce commit-ovati (automatski rollback)
     */
    default public void commit() throws Exception {
        DBBroker.getInstance().establishConnection().commit();
    } 
    /**
     * default javna metoda za rollback transakcije
     * @throws Exception ako nije moguce rollback-ovati 
     */
    default public void rollback() throws Exception {
        DBBroker.getInstance().establishConnection().rollback();
    } 
}
