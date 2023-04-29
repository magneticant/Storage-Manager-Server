//package operation.product;
package rs.np.storage_manager_server.operation.product;

import rs.np.storage_manager_common.domain.Product;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 *
 * @author Milan
 */
public class UpdateProduct extends GenericSystemOperation{

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
