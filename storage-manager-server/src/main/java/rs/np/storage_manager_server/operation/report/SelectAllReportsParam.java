package rs.np.storage_manager_server.operation.report;
import java.util.List;

import rs.np.storage_manager_common.domain.Report;
import rs.np.storage_manager_common.domain.ReportItem;
import rs.np.storage_manager_server.controller.Controller;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 * Klasa za prikupljanje podataka o svim izvestajima, parametrizovano, po datumu. Nasledjuje klasu 
 * {@link rs.np.storage_manager_server.operation.GenericSystemOperation}.
 * 
 * @author Milan
 */
public class SelectAllReportsParam extends GenericSystemOperation {
	/**
	 * privatni atribut reports, lista izvestaja koje treba preuzeti iz baze podataka
	 */
	private List<Report> reports;
	/**
	 * preduslovi za transakciju
	 * @param parameter, tipa {@link Object}. Objekat nad kojim se vrsi sistemska operacija
	 * @throws Exception ako je parametar null ili nije tipa {@link Report}
	 */
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
    /**
     * get metoda za izvestaje
     * 
     * @return reports, kao lista izvestaja (Report)
     */
    public List<Report> getReports() {
        return reports;
    }
    /**
     * privatna metoda koja dodeljuje svaku stavku odgovarajucem izvestaju
     * @param items kao lista stavki izvestaja (ReportItem). Lista stavki izvestaja koje treba dodeliti
     * izvestajima
     */
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
