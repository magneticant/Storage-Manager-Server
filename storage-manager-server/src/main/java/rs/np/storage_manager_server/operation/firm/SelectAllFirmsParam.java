//package operation.firm;
package rs.np.storage_manager_server.operation.firm;

import java.util.ArrayList;
import java.util.List;

import rs.np.storage_manager_common.domain.Firm;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 * Klasa za prikupljanje podataka o svim firmama, parametrizovano, po nazivu firme. Nasledjuje klasu 
 * {@link rs.np.storage_manager_server.operation.GenericSystemOperation}.
 * 
 * @author Milan
 */
public class SelectAllFirmsParam extends GenericSystemOperation {
	/**
	 * privatni atribut firms, lista firmi koje treba preuzeti iz baze podataka
	 */
    private List<Firm> firms;
    
    @Override
    protected void preconditions(Object parameter) throws Exception { }
    
    @SuppressWarnings("unchecked")
	@Override
    protected void executeOperation(Object parameter) throws Exception {
        firms = repository.selectAll((Firm)parameter);
    }
    /**
     * get metoda za firme
     * 
     * @return firms, kao lista firmi ({@link ArrayList}) 
     */
    public List<Firm> getFirms() {
        return firms;
    }
}
