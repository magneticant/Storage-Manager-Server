//package operation;
package rs.np.storage_manager_server.operation;

import rs.np.storage_manager_server.repository.DBRepository;
import rs.np.storage_manager_server.repository.Repository;
import rs.np.storage_manager_server.repository.implementation.AbstractDBRepository;

//import repository.DBRepository;
//import repository.Repository;
//import repository.implementation.AbstractDBRepository;

/**
 * Apstraktna klasa za sistemske operacije. Sadrzi sablon za rad sa transakcijom zadat kao
 * Template Method Pattern. 
 * 
 * @author Milan 
 */
public abstract class GenericSystemOperation {
	/**
	 * protected scope (zasticeni) atribut Repository koji je final i predstavlja komponentu
	 * dependency injection-a (preko konstruktora) za rad sa apstraktnim repozitorijumom
	 */
    protected final Repository repository;
    /**
     * neparametrizovani konstruktor klase. Postavlja dependency repozitorijuma (vidi: repository)
     */
    public GenericSystemOperation() {
        this.repository = (Repository) new AbstractDBRepository();
    }
    /**
     * template method za rad sa jednom transakcijom. Poziva apstraktne metode preconditions,
     * startTransaction, executeOperation i commit
     * 
     * @param parameter tipa Object. Predstavlja objekat koji se perzistira
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
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
    /**
     * zasticena (protected scope) apstraktna metoda preconditions koja predstavlja preduslove za izvrsenje S.O.
     * @param parameter tipa Object, predstavlja objekat koji se perzistira
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
    protected abstract void preconditions(Object parameter) throws Exception;
    /**
     * privatna metoda startTransaction, koja zapocinje transakciju uspostavljanjem veze sa bazom podataka
     * @throws Exception u slucaju da nije moguce uspostaviti vezu sa bazom podataka
     */
    private void startTransaction() throws Exception {
        ((DBRepository)repository).connect();
    }
    /**
     * zasticena (protected scope) apstraktna metoda preconditions koja predstavlja izvrsenje S.O.
     * @param parameter tipa Object, predstavlja objekat koji se perzistira
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
    protected abstract void executeOperation(Object parameter) throws Exception;
    
    /**
     * privatna metoda commit koja commituje operaciju
     * @throws Exception u slucaju greske prilikom commit-ovanja (izvrsen rollback)
     */
    private void commit() throws Exception {
        ((DBRepository)repository).commit();
    }
    /**
     * privatna rollback metoda
     * 
     * @throws Exception u slucaju greske prilikom rollback-ovanja
     */
    private void rollback() throws Exception {
        ((DBRepository)repository).rollback();
    }
    /**
     * privatna metoda disconnect
     * 
     * @throws Exception u slucaju greske prilikom disconnect-ovanja sa baze podataka
     */
    private void disconnect() throws Exception {
            ((DBRepository)repository).disconnect();
    }
}
