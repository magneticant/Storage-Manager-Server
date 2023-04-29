//package operation;
package rs.np.storage_manager_server.operation;

import rs.np.storage_manager_server.repository.DBRepository;
import rs.np.storage_manager_server.repository.Repository;
import rs.np.storage_manager_server.repository.implementation.AbstractDBRepository;

//import repository.DBRepository;
//import repository.Repository;
//import repository.implementation.AbstractDBRepository;

/**
 *
 * @author Milan
 */
public abstract class GenericSystemOperation {
    protected final Repository repository;

    public GenericSystemOperation() {
        this.repository = (Repository) new AbstractDBRepository();
    }
    
    public final void execute(Object parameter) throws Exception{
       try{
        preconditions(parameter);
        startTransaction();
        executeOperation(parameter);
        commit();
       }catch(Exception ex){
           System.out.println("Error while processing request.");
           ex.printStackTrace();
           rollback();
           throw ex;
       } finally {
           disconnect();
       }
    } 

    protected abstract void preconditions(Object parameter) throws Exception;

    private void startTransaction() throws Exception {
        ((DBRepository)repository).connect();
    }

    protected abstract void executeOperation(Object parameter) throws Exception;

    private void commit() throws Exception {
        ((DBRepository)repository).commit();
    }

    private void rollback() throws Exception {
        ((DBRepository)repository).rollback();
    }

    private void disconnect() throws Exception {
            ((DBRepository)repository).disconnect();
    }
}
