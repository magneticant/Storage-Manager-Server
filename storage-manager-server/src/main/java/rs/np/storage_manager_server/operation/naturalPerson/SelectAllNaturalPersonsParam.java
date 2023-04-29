//package operation.naturalPerson;\
package rs.np.storage_manager_server.operation.naturalPerson;

import java.util.List;

import rs.np.storage_manager_common.domain.abstraction.implementation.NaturalPerson;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 *
 * @author Milan
 */
public class SelectAllNaturalPersonsParam extends GenericSystemOperation {
    private List<NaturalPerson> naturalPersons;
    
    @Override
    protected void preconditions(Object parameter) throws Exception { }

    @Override
    protected void executeOperation(Object parameter) throws Exception {
        naturalPersons = repository.selectAll((NaturalPerson)parameter);
    }

    public List<NaturalPerson> getNaturalPersons() {
        return naturalPersons;
    }
    
}
