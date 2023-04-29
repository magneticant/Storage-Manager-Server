//package operation.legalPerson;
package rs.np.storage_manager_server.operation.legalPerson;

//import domain.abstraction.implementation.LegalPerson;
import java.util.List;
//import operation.GenericSystemOperation;

import rs.np.storage_manager_common.domain.abstraction.implementation.LegalPerson;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 *
 * @author Milan
 */
public class SelectAllLegalPersonsParam extends GenericSystemOperation {
    private List<LegalPerson> legalPersons;
    
    @Override
    protected void preconditions(Object parameter) throws Exception {
    }

    @Override
    protected void executeOperation(Object parameter) throws Exception {
        legalPersons = repository.selectAll((LegalPerson)parameter);
    }

    public List<LegalPerson> getLegalPersons() {
        return legalPersons;
    }
}
