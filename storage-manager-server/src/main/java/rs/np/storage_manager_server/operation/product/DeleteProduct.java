//package operation.product;
package rs.np.storage_manager_server.operation.product;

import rs.np.storage_manager_common.domain.Product;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 * Klasa za brisanje proizvoda. Nasledjuje klasu {@link rs.np.storage_manager_server.operation.GenericSystemOperation}.
 * 
 * @author Milan
 */
public class DeleteProduct extends GenericSystemOperation{
	/**
	 * preduslovi za transakciju
	 * @param parameter, tipa {@link Object}. Objekat nad kojim se vrsi sistemska operacija
	 * @throws Exception ako je parametar null ili nije tipa {@link Product}
	 */
    @Override
    protected void preconditions(Object parameter) throws Exception {
        if(parameter == null || !(parameter instanceof Product)){
           throw new Exception("Please input a valid product to delete.");
        }
            
    }

    @Override
    protected void executeOperation(Object parameter) throws Exception {
        repository.delete((Product)parameter);
    }
    
}
