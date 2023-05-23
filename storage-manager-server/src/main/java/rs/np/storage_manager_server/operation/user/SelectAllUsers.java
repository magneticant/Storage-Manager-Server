//package operation.user;
package rs.np.storage_manager_server.operation.user;

import java.util.ArrayList;
import java.util.List;

import rs.np.storage_manager_common.domain.User;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 * Klasa za prikupljanje podataka o svim korisnicima. Nasledjuje klasu 
 * {@link rs.np.storage_manager_server.operation.GenericSystemOperation}.
 * 
 * @author Milan
 */
public class SelectAllUsers extends GenericSystemOperation{
	/**
	 * privatni atribut users, lista korisnika koje treba preuzeti iz baze podataka
	 */
	private List<User> users;

    @Override
    protected void preconditions(Object parameter) throws Exception {}

    @SuppressWarnings("unchecked")
	@Override
    protected void executeOperation(Object parameter) throws Exception {
        users = repository.selectAll();
    }
    /**
     * get metoda za korisnike
     * 
     * @return users, kao lista korisnika ({@link ArrayList})
     */
    public List<User> getUsers() {
        return users;
    }
    
}
