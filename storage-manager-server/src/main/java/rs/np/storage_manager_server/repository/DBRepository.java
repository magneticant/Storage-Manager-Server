//package repository;
package rs.np.storage_manager_server.repository;

/**
 *
 * @author Milan
 */
public interface DBRepository<T> extends Repository<T> {
    default public void connect()throws Exception{
        DBBroker.getInstance().establishConnection();
    }
    default public void disconnect() throws Exception {
        DBBroker.getInstance().establishConnection().close();
    } 
    default public void commit() throws Exception {
        DBBroker.getInstance().establishConnection().commit();
    } 
    default public void rollback() throws Exception {
        DBBroker.getInstance().establishConnection().rollback();
    } 
}
