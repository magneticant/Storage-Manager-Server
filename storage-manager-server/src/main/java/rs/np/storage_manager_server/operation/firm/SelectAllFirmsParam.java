//package operation.firm;
package rs.np.storage_manager_server.operation.firm;

import java.util.List;

import rs.np.storage_manager_common.domain.Firm;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 * Klasa za prikupljanje podataka o svim firmama, parametrizovano, po nazivu firme. Nasledjuje klasu 
 * GenericSystemOperation.
 * 
 * @author Milan
 */
public class SelectAllFirmsParam extends GenericSystemOperation {
	/**
	 * privatni atribut firms, tipa List<Firm>, lista firmi koje treba preuzeti iz baze podataka
	 */
    private List<Firm> firms;
    
    @Override
    protected void preconditions(Object parameter) throws Exception { }
    
    @Override
    protected void executeOperation(Object parameter) throws Exception {
        firms = repository.selectAll((Firm)parameter);
    }
    /**
     * get metoda za firme
     * 
     * @return firms, kao List<Firm> (ArrayList)
     */
    public List<Firm> getFirms() {
        return firms;
    }
}
