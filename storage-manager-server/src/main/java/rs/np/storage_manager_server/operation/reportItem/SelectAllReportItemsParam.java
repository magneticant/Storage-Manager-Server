//package operation.reportItem;
package rs.np.storage_manager_server.operation.reportItem;

import java.util.ArrayList;
import java.util.List;

import rs.np.storage_manager_common.domain.ReportItem;
import rs.np.storage_manager_server.operation.GenericSystemOperation;
import rs.np.storage_manager_server.repository.implementation.concrete.DBRepositoryReportItem;
/**
 * Klasa za prikupljanje podataka o svim stavkama izvestaja, parametrizovano, po datumu. Nasledjuje klasu 
 * {@link rs.np.storage_manager_server.operation.GenericSystemOperation}.
 * 
 * @author Milan
 */
public class SelectAllReportItemsParam extends GenericSystemOperation {
	/**
	 * privatni atribut items, lista stavki izvestaja koje treba preuzeti iz baze podataka
	 */
	private List<ReportItem> items;
    
	/**
	 * preduslovi za transakciju
	 * @param parameter, tipa {@link Object}. Objekat nad kojim se vrsi sistemska operacija
	 * @throws Exception ako je parametar null ili nije tipa {@link ReportItem}
	 */
    @Override
    protected void preconditions(Object parameter) throws Exception {
        if(parameter == null || !(parameter instanceof ReportItem)){
            throw new Exception("Please input a valid report item to select");
        }
    }
    /**
     * Override metode "executeOperation", gde se prvo vracaju sve stavke izvestaja 
     * parametrizovano, a zatim se vracaju i svi proizvodi iz baze, pa se svakoj stavci
     * izvestaja dodeljuje adekvatan proizvod po ID-ju preko privatne metode assignProductToReportItem
     */
	@Override
    protected void executeOperation(Object parameter) throws Exception {
        items = new DBRepositoryReportItem().selectAll();
    }
    
	/**
     * get metoda za stavke izvestaja 
     * @return items, kao lista stavki izvestaja ({@link ReportItem}, {@link ArrayList})
     */
    public List<ReportItem> getItems() {
        return items;
    }
}
