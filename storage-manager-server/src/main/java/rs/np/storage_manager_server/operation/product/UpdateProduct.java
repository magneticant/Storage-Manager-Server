//package operation.product;
package rs.np.storage_manager_server.operation.product;

import rs.np.storage_manager_common.domain.Product;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 * Klasa za izmenu proizvoda u bazi podataka. Nasledjuje klasu GenericSystemOperation.
 * 
 * @author Milan
 */
public class UpdateProduct extends GenericSystemOperation{
	/**
	 * preduslovi za transakciju
	 * @param parameter, tipa Object. Objekat nad kojim se vrsi sistemska operacija
	 * @throws Exception ako je parametar null ili nije tipa Product
	 */
    @Override
    protected void preconditions(Object parameter) throws Exception {
    if(parameter == null || !(parameter instanceof Product)){
           throw new Exception("Please input a valid product to update.");
        }
    }

    @Override
    protected void executeOperation(Object parameter) throws Exception {
        repository.update((Product)parameter);
    }
    
}
