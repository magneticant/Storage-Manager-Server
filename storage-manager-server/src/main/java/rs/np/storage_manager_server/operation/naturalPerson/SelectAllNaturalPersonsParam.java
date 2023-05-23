//package operation.naturalPerson;\
package rs.np.storage_manager_server.operation.naturalPerson;

import java.util.ArrayList;
import java.util.List;

import rs.np.storage_manager_common.domain.abstraction.implementation.NaturalPerson;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 * Klasa za prikupljanje podataka o svim fizickim licima, parametrizovano, po imenu fizickog lica. Nasledjuje klasu 
 * {@link rs.np.storage_manager_server.operation.GenericSystemOperation}.
 * 
 * @author Milan
 */
public class SelectAllNaturalPersonsParam extends GenericSystemOperation {
	/**
	 * privatni atribut naturalPersons, lista firmi koje treba preuzeti iz baze podataka
	 */
	private List<NaturalPerson> naturalPersons;
    
    @Override
    protected void preconditions(Object parameter) throws Exception { }

    @SuppressWarnings("unchecked")
	@Override
    protected void executeOperation(Object parameter) throws Exception {
        naturalPersons = repository.selectAll((NaturalPerson)parameter);
    }
    /**
     * get metoda za fizicka lica
     * 
     * @return firms, kao lista fizickih lica ({@link NaturalPerson}, {@link ArrayList} )
     */
    public List<NaturalPerson> getNaturalPersons() {
        return naturalPersons;
    }
    
}
