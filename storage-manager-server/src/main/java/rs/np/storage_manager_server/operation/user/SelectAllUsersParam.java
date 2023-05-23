//package operation.user;
package rs.np.storage_manager_server.operation.user;

import java.util.ArrayList;
import java.util.List;

import rs.np.storage_manager_common.domain.User;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 * Klasa za prikupljanje podataka o svim korisnicima, parametrizovano, po ID-ju ili korisnickom imenu/sifri.
 * Nasledjuje klasu {@link rs.np.storage_manager_server.operation.GenericSystemOperation}.
 * 
 * @author Milan
 */
public class SelectAllUsersParam extends GenericSystemOperation{
	/**
	 * privatni atribut users, lista korisnika koje treba preuzeti iz baze podataka
	 */
	private List<User> users;
	/**
	 * preduslovi za transakciju
	 * @param parameter, tipa {@link Object}. Objekat nad kojim se vrsi sistemska operacija
	 * @throws Exception ako je parametar null ili nije tipa {@link User}
	 */
    @Override
    protected void preconditions(Object parameter) throws Exception {
        if(parameter == null || !(parameter instanceof User)){
           throw new Exception("Please input a valid user to fetch.");
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    protected void executeOperation(Object parameter) throws Exception {
        users = repository.selectAll((User)parameter);
    }
    /**
     * get metoda za korisnike
     * @return users, kao lista korisnika ( {@link ArrayList} )
     */
    public List<User> getUsers() {
        return users;
    }
 
    
}
