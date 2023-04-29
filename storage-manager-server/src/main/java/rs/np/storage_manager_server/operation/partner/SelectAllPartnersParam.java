//package operation.partner;
package rs.np.storage_manager_server.operation.partner;

import java.util.List;

import rs.np.storage_manager_common.domain.Partner;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 *
 * @author Milan
 */
public class SelectAllPartnersParam extends GenericSystemOperation {
    private List<Partner> partners;
    
    @Override
    protected void preconditions(Object parameter) throws Exception { }

    @Override
    protected void executeOperation(Object parameter) throws Exception {
        partners = repository.selectAll((Partner)parameter);
    }

    public List<Partner> getPartners() {
        return partners;
    }
}
