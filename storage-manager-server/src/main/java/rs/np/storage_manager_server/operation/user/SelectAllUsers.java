//package operation.user;
package rs.np.storage_manager_server.operation.user;

import java.util.List;

import rs.np.storage_manager_common.domain.User;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 * Klasa za prikupljanje podataka o svim korisnicima. Nasledjuje klasu 
 * GenericSystemOperation.
 * 
 * @author Milan
 */
public class SelectAllUsers extends GenericSystemOperation{
	/**
	 * privatni atribut users, tipa List<User>, lista korisnika koje treba preuzeti iz baze podataka
	 */
	private List<User> users;

    @Override
    protected void preconditions(Object parameter) throws Exception {}

    @Override
    protected void executeOperation(Object parameter) throws Exception {
        users = repository.selectAll();
    }
    /**
     * get metoda za korisnike
     * 
     * @return users, kao List<User> (ArrayList)
     */
    public List<User> getUsers() {
        return users;
    }
    
}
