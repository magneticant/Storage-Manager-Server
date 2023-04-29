//package controller;
package rs.np.storage_manager_server.controller;

import java.util.List;

import rs.np.storage_manager_common.domain.*;
import rs.np.storage_manager_common.domain.abstraction.*;
import rs.np.storage_manager_common.domain.abstraction.implementation.*;
import rs.np.storage_manager_server.operation.GenericSystemOperation;
import rs.np.storage_manager_server.operation.firm.SelectAllFirmsParam;
import rs.np.storage_manager_server.operation.legalPerson.SelectAllLegalPersonsParam;
import rs.np.storage_manager_server.operation.naturalPerson.SelectAllNaturalPersonsParam;
import rs.np.storage_manager_server.operation.partner.SelectAllPartnersParam;
import rs.np.storage_manager_server.operation.product.DeleteProduct;
import rs.np.storage_manager_server.operation.product.InsertProduct;
import rs.np.storage_manager_server.operation.product.SelectAllProductsParam;
import rs.np.storage_manager_server.operation.product.UpdateProduct;
import rs.np.storage_manager_server.operation.report.SelectAllReportsParam;
import rs.np.storage_manager_server.operation.reportItem.SelectAllReportItemsParam;
import rs.np.storage_manager_server.operation.user.SelectAllUsersParam;
import rs.np.storage_manager_server.repository.DBRepository;
import rs.np.storage_manager_server.repository.Repository;
import rs.np.storage_manager_server.repository.implementation.concrete.*;
import rs.np.storage_manager_server.operation.user.*;
/**
 *
 * @author Milan
 */
public class Controller {
    private final Repository repositoryUser;
    private final Repository repositoryReport;
    private final Repository repositoryProduct;
    private final Repository repositoryGoodsReceived;
    private final Repository repositoryPartner;
    private final Repository repositoryFirm;
    private final Repository repositoryNaturalPerson;
    private final Repository repositoryLegalPerson;
    private final Repository repositoryBillOFLading;
            
    private static Controller instance;

    private Controller() {
        this.repositoryUser =  new DBRepositoryUser();
        this.repositoryReport = new DBRepositoryReport();
        this.repositoryProduct = new DBRepositoryProduct();
        this.repositoryGoodsReceived = new DBRepositoryGoodsReceivedNote();
        this.repositoryPartner = new DBRepositoryBusinessPartner();
        this.repositoryFirm = new DBRepositoryFirm();
        this.repositoryNaturalPerson = new DBRepositoryNaturalPerson();
        this.repositoryLegalPerson = new DBRepositoryLegalPerson();
        this.repositoryBillOFLading = new DBRepositoryBillOfLading();
    }
    
    public static Controller getInstance(){
        if(instance == null)
            instance = new Controller();
        
        return instance;
    }
    
    public User login(String username, String password) throws Exception{
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setMode(WhereClauseMode.BY_USERNAME_PASSWORD);
        GenericSystemOperation SO = new SelectAllUsersParam();
        SO.execute(user);
        List<User> users = ((SelectAllUsersParam)SO).getUsers();
        
        if(users == null || users.isEmpty())
            throw new Exception("User not found. Try again.");
        else
            return users.get(0);
    }
    
    public List<Product> getAllProducts() throws Exception {
        GenericSystemOperation SO = new SelectAllProductsParam();
        SO.execute(new Product());
        return ((SelectAllProductsParam)SO).getProducts();
    }

    public List<Product> getAllProducts(Product product) throws Exception {
        product.setMode(WhereClauseMode.BY_NAME);
        GenericSystemOperation SO = new SelectAllProductsParam();
        SO.execute(product);
        return ((SelectAllProductsParam)SO).getProducts();
//        return repositoryProduct.selectAll(product);
    }

    public void updateProduct(Product productUpdate) throws Exception {
        GenericSystemOperation SO = new UpdateProduct();
        SO.execute(productUpdate);
    }

    public void deleteProduct(Product productForDeletion) throws Exception {
        GenericSystemOperation SO = new DeleteProduct();
        SO.execute(productForDeletion);
    }

    public void insertProduct(Product productForInsert) throws Exception {
        GenericSystemOperation SO = new InsertProduct();
        SO.execute(productForInsert);
    }

    public void insertReport(Report reportForInsertion) throws Exception {
        ((DBRepositoryReport)repositoryReport).connect();
        try{ 
            repositoryReport.insert(reportForInsertion);
            ((DBRepositoryReport)repositoryReport).commit();
        } catch(Exception ex){
            ex.printStackTrace();
            ((DBRepositoryReport)repositoryReport).rollback();
            throw ex;
        } finally {
            ((DBRepository) repositoryReport).disconnect();
        }
    }

    public List<Partner> getAllPartners() throws Exception {
//        List<Partner> partners;
//        partners = repositoryPartner.selectAll();
//        return partners;
          GenericSystemOperation SO = new SelectAllPartnersParam();
          SO.execute(new Partner());
          return ((SelectAllPartnersParam)SO).getPartners();
          
    }

    public List<Firm> getAllFirms() throws Exception {
//        return repositoryFirm.selectAll();
        GenericSystemOperation SO = new SelectAllFirmsParam();
        SO.execute(new Firm());
        return ((SelectAllFirmsParam)SO).getFirms();
    }

    public void insertGoodsReceivedNote(GoodsReceivedNote noteForInsert) throws Exception {
        List<? extends AbstractDocumentItem> items = noteForInsert.getItems();
        alterProductStock(items);
        
        ((DBRepository)repositoryGoodsReceived).connect();
        try{
            repositoryGoodsReceived.insert(noteForInsert);
            ((DBRepository)repositoryGoodsReceived).commit();
        } catch(Exception ex){
            ex.printStackTrace();
            ((DBRepository)repositoryGoodsReceived).rollback();
            throw ex;
        } finally{
               ((DBRepository)repositoryGoodsReceived).disconnect();
        }
    }

    public List<NaturalPerson> getAllNaturalPersons() throws Exception {
//        return repositoryNaturalPerson.selectAll();
        GenericSystemOperation SO = new SelectAllNaturalPersonsParam();
        SO.execute(new NaturalPerson());
        return ((SelectAllNaturalPersonsParam)SO).getNaturalPersons();
    }

    public List<LegalPerson> getAllLegalPersons() throws Exception {
//        return repositoryLegalPerson.selectAll();
        GenericSystemOperation SO = new SelectAllLegalPersonsParam();
        SO.execute(new LegalPerson());
        return ((SelectAllLegalPersonsParam)SO).getLegalPersons();
        
    }

    public void insertBillOfLading(BillOfLading billForInsert) throws Exception {
        List<? extends AbstractDocumentItem> items = billForInsert.getItems();
        alterProductStock(items);
        
        ((DBRepositoryBillOfLading)repositoryBillOFLading).connect();
        try{
            repositoryBillOFLading.insert(billForInsert);
            ((DBRepositoryBillOfLading)repositoryBillOFLading).commit();
        }catch(Exception ex){
            ex.printStackTrace();
            ((DBRepositoryBillOfLading)repositoryBillOFLading).rollback();
            throw ex;
        } finally {
            ((DBRepository)repositoryBillOFLading).disconnect();
        }
        
    }

    public List<Report> getAllReports(Report reportForSearch) throws Exception {
        GenericSystemOperation SO = new SelectAllReportsParam();
        SO.execute(reportForSearch);
        
        return ((SelectAllReportsParam)SO).getReports();
    }

    public List<ReportItem> getAllReportItems() throws Exception {
        GenericSystemOperation SO = new SelectAllReportItemsParam();
        SO.execute(new ReportItem());
        return ((SelectAllReportItemsParam)SO).getItems();
    }

    private void alterProductStock(List<? extends AbstractDocumentItem> items) throws Exception {
        for(AbstractDocumentItem item : items){
            if(item.getProduct()!=null){
                    int alteredStock = item.alterStock(item.getAmount());
                    item.getProduct().setAmount(alteredStock);
                    item.getProduct().setMode(WhereClauseMode.BY_ID);
                    updateProduct(item.getProduct());
            }
            }
        }
        
    }
