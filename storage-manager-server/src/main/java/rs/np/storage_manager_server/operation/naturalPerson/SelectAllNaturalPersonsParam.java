//package operation.naturalPerson;\
package rs.np.storage_manager_server.operation.naturalPerson;

import java.util.List;

import rs.np.storage_manager_common.domain.abstraction.implementation.NaturalPerson;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 * Klasa za prikupljanje podataka o svim fizickim licima, parametrizovano, po imenu fizickog lica. Nasledjuje klasu 
 * GenericSystemOperation.
 * 
 * @author Milan
 */
public class SelectAllNaturalPersonsParam extends GenericSystemOperation {
	/**
	 * privatni atribut naturalPersons, tipa List<NaturalPerson>, lista firmi koje treba preuzeti iz baze podataka
	 */
	private List<NaturalPerson> naturalPersons;
    
    @Override
    protected void preconditions(Object parameter) throws Exception { }

    @Override
    protected void executeOperation(Object parameter) throws Exception {
        naturalPersons = repository.selectAll((NaturalPerson)parameter);
    }
    /**
     * get metoda za fizicka lica
     * 
     * @return firms, kao List<NaturalPerson> (ArrayList)
     */
    public List<NaturalPerson> getNaturalPersons() {
        return naturalPersons;
    }
    
}
