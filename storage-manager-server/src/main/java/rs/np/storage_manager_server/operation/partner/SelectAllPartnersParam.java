//package operation.partner;
package rs.np.storage_manager_server.operation.partner;

import java.util.List;

import rs.np.storage_manager_common.domain.Partner;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 * Klasa za prikupljanje podataka o svim poslovnim partnerima, parametrizovano, po nazivu partnera. Nasledjuje klasu 
 * GenericSystemOperation.
 * 
 * @author Milan
 */
public class SelectAllPartnersParam extends GenericSystemOperation {
	/**
	 * privatni atribut partners, tipa List<Partner>, lista poslovnih partnera koje treba preuzeti iz baze podataka
	 */
	private List<Partner> partners;
    
    @Override
    protected void preconditions(Object parameter) throws Exception { }

    @Override
    protected void executeOperation(Object parameter) throws Exception {
        partners = repository.selectAll((Partner)parameter);
    }
    /**
     * get metoda za poslovne partnere
     * 
     * @return partners, kao List<Partner> (ArrayList)
     */
    public List<Partner> getPartners() {
        return partners;
    }
}
