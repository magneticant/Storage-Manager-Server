//package operation.legalPerson;
package rs.np.storage_manager_server.operation.legalPerson;

import java.util.ArrayList;
//import domain.abstraction.implementation.LegalPerson;
import java.util.List;
//import operation.GenericSystemOperation;

import rs.np.storage_manager_common.domain.abstraction.implementation.LegalPerson;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 * Klasa za prikupljanje podataka o svim pravnim licima, parametrizovano, po nazivu. Nasledjuje klasu 
 * {@link rs.np.storage_manager_server.operation.GenericSystemOperation}.
 * 
 * @author Milan
 */
public class SelectAllLegalPersonsParam extends GenericSystemOperation {
	/**
	 * privatni atribut legalPersons, lista pravnih lica koje treba preuzeti iz baze podataka
	 */
	private List<LegalPerson> legalPersons;
    
    @Override
    protected void preconditions(Object parameter) throws Exception {
    }

    @SuppressWarnings("unchecked")
	@Override
    protected void executeOperation(Object parameter) throws Exception {
        legalPersons = repository.selectAll((LegalPerson)parameter);
    }
    /**
     * get metoda za pravna lica
     * 
     * @return legalPersons, kao lista pravnih lica (LegalPerson, {@link ArrayList} )
     */
    public List<LegalPerson> getLegalPersons() {
        return legalPersons;
    }
}
