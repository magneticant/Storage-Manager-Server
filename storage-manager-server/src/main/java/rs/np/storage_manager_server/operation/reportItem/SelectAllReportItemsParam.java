//package operation.reportItem;
package rs.np.storage_manager_server.operation.reportItem;

import java.util.List;

import rs.np.storage_manager_common.domain.Product;
import rs.np.storage_manager_common.domain.ReportItem;
import rs.np.storage_manager_server.controller.Controller;
import rs.np.storage_manager_server.operation.GenericSystemOperation;
/**
 *
 * @author Milan
 */
public class SelectAllReportItemsParam extends GenericSystemOperation {
    private List<ReportItem> items;
    
    @Override
    protected void preconditions(Object parameter) throws Exception {
        if(parameter == null || !(parameter instanceof ReportItem)){
            throw new Exception("Please input a valid report item to select");
        }
    }

    @Override
    protected void executeOperation(Object parameter) throws Exception {
        items = repository.selectAll((ReportItem)parameter);
        List<Product> products;
        
        products = Controller.getInstance().getAllProducts();
        assignProductToReportItem(products);
    }

    public List<ReportItem> getItems() {
        return items;
    }

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
