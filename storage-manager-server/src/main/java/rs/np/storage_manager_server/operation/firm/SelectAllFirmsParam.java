//package operation.firm;
package rs.np.storage_manager_server.operation.firm;

import java.util.List;

import rs.np.storage_manager_common.domain.Firm;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 *
 * @author Milan
 */
public class SelectAllFirmsParam extends GenericSystemOperation {
    private List<Firm> firms;
    
    @Override
    protected void preconditions(Object parameter) throws Exception { }

    @Override
    protected void executeOperation(Object parameter) throws Exception {
        firms = repository.selectAll((Firm)parameter);
    }

    public List<Firm> getFirms() {
        return firms;
    }
}
