//package operation.product;
package rs.np.storage_manager_server.operation.product;

import java.util.List;

import rs.np.storage_manager_common.domain.Product;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 *
 * @author Milan
 */
public class SelectAllProducts extends GenericSystemOperation{
    private List<Product> products;

    @Override
    protected void preconditions(Object parameter) throws Exception {}

    @Override
    protected void executeOperation(Object parameter) throws Exception {
        products = repository.selectAll();
    }

    public List<Product> getProducts() {
        return products;
    }
}
