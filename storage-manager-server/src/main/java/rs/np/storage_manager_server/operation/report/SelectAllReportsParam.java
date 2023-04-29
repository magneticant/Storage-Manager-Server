package rs.np.storage_manager_server.operation.report;
import java.util.List;

import rs.np.storage_manager_common.domain.Report;
import rs.np.storage_manager_common.domain.ReportItem;
import rs.np.storage_manager_server.controller.Controller;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 *
 * @author Milan
 */
public class SelectAllReportsParam extends GenericSystemOperation {
    private List<Report> reports;
    
    @Override
    protected void preconditions(Object parameter) throws Exception {
    if(parameter == null || !(parameter instanceof Report)){
           throw new Exception("Please input a valid report to select.");
        }
    }

    @Override
    protected void executeOperation(Object parameter) throws Exception {
        reports = repository.selectAll((Report)parameter);
        List<ReportItem> items;
        
          
        items = Controller.getInstance().getAllReportItems();
        assignItemToReport(items);
        
    }

    public List<Report> getReports() {
        return reports;
    }

    private void assignItemToReport(List<ReportItem> items) {
        for(Report report : reports){
            for(ReportItem item: items){
                if(item == null || item.getReport() == null)
                    continue;
                if(report.getReportDate().equals(item.getReport().getReportDate())){
                    report.getReportItems().add(item);
                }
                    
            }
        }
    }
    
}
