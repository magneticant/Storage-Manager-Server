//package operation.reportItem;
package rs.np.storage_manager_server.operation.reportItem;

import java.util.List;

import rs.np.storage_manager_common.domain.Product;
import rs.np.storage_manager_common.domain.ReportItem;
import rs.np.storage_manager_server.controller.Controller;
import rs.np.storage_manager_server.operation.GenericSystemOperation;
/**
 * Klasa za prikupljanje podataka o svim stavkama izvestaja, parametrizovano, po datumu. Nasledjuje klasu 
 * GenericSystemOperation.
 * 
 * @author Milan
 */
public class SelectAllReportItemsParam extends GenericSystemOperation {
	/**
	 * privatni atribut items, tipa List<ReportItem>, lista stavki izvestaja koje treba preuzeti iz baze podataka
	 */
	private List<ReportItem> items;
    
	/**
	 * preduslovi za transakciju
	 * @param parameter, tipa Object. Objekat nad kojim se vrsi sistemska operacija
	 * @throws Exception ako je parametar null ili nije tipa ReportItem
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
        items = repository.selectAll((ReportItem)parameter);
        List<Product> products;
        
        products = Controller.getInstance().getAllProducts();
        assignProductToReportItem(products);
    }
    /**
     * get metoda za stavke izvestaja 
     * @return items, tipa List<ReportItem>
     */
    public List<ReportItem> getItems() {
        return items;
    }
    /**
     * privatna metoda koja dodeljuje svaki proizvod odgovarajucoj stavci izvestaja
     * @param items tipa List<Product>. Lista proizvoda koje treba dodeliti
     * stavkama izvestaja
     */
    private void assignProductToReportItem(List<Product> products) {
        for(ReportItem item : items){
            for(Product product: products){
                if(product == null || item.getProduct() == null)
                    continue;
                if(product.getID() == item.getProduct().getID()){
                    item.setProduct(product);
                }
            }
            System.out.println("REPORT ITEMS: " + item);
        }
    }
}
