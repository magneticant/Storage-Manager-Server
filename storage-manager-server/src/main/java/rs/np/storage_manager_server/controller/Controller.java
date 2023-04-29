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
 * Kontroler klasa projekta. Kontroler prima od ClientThread klase zahteve klijenata i salje
 * (u najvecem broju slucajeva) GenericSystemOperation-u na perzistiranje.
 * 
 * @author Milan
 */
public class Controller {
	/**
	 * privatni atribut repozitorijum korisnika
	 */
    private final Repository repositoryUser;
    /**
	 * privatni atribut repozitorijum izvestaja
	 */
    private final Repository repositoryReport;
    /**
	 * privatni atribut repozitorijum proizvoda
	 */
    private final Repository repositoryProduct;
    /**
	 * privatni atribut repozitorijum prijemnica
	 */
    private final Repository repositoryGoodsReceived;
    /**
	 * privatni atribut repozitorijum poslovnih partnera
	 */
    private final Repository repositoryPartner;
    /**
	 * privatni atribut repozitorijum firmi
	 */
    private final Repository repositoryFirm;
    /**
	 * privatni atribut repozitorijum fizickih lica
	 */
    private final Repository repositoryNaturalPerson;
    /**
	 * privatni atribut repozitorijum pravnih lica
	 */
    private final Repository repositoryLegalPerson;
    /**
	 * privatni atribut repozitorijum otpremnica
	 */
    private final Repository repositoryBillOFLading;
    /**
     * privatni atribut kontroler instance (za singleton pattern).
     */
    private static Controller instance;
    /**
     * neparametarski konstruktor koji inicijalizuje sve repository-jume na odgovarajuci tip.
     */
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
    /**
     * getInstance metoda implementirana preko Singleton pattern-a.
     * 
     * @return instance tipa Controller.
     */
    public static Controller getInstance(){
        if(instance == null)
            instance = new Controller();
        
        return instance;
    }
    /**
     * Login metoda klijenata na serverskoj strani
     * @param username korisnicko ime kao String
     * @param password sifra kao String
     * @return prijavljeni korisnik kao tip User
     * @throws Exception u slucaju da korisnik nije pronadjen
     */
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
    /**
     * metoda koja poziva sistemsku operaciju da vrati sve proizvode iz baze podataka
     * 
     * @return lista proizvoda List<Products>
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
    public List<Product> getAllProducts() throws Exception {
        GenericSystemOperation SO = new SelectAllProductsParam();
        SO.execute(new Product());
        return ((SelectAllProductsParam)SO).getProducts();
    }
    /**
     * metoda koja poziva sistemsku operaciju da vrati sve proizvode SA ZADATIM IMENOM 
     * iz baze podataka
     * 
     * @param product tipa Product, proizvod sa informacijom o imenu za pretragu
     * @return lista proizvoda List<Products>
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
    public List<Product> getAllProducts(Product product) throws Exception {
        product.setMode(WhereClauseMode.BY_NAME);
        GenericSystemOperation SO = new SelectAllProductsParam();
        SO.execute(product);
        return ((SelectAllProductsParam)SO).getProducts();
//        return repositoryProduct.selectAll(product);
    }
    /**
     * metoda koja poziva sistemsku operaciju da izmeni proizvod u bazi podataka
     * 
     * @param productUpdate tipa Product, proizvod za izmenu u bazi.
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
    public void updateProduct(Product productUpdate) throws Exception {
        GenericSystemOperation SO = new UpdateProduct();
        SO.execute(productUpdate);
    }
    /**
     * metoda koja poziva sistemsku operaciju da obrise proizvod u bazi podataka
     * 
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
    public void deleteProduct(Product productForDeletion) throws Exception {
        GenericSystemOperation SO = new DeleteProduct();
        SO.execute(productForDeletion);
    }
    /**
     * metoda koja poziva sistemsku operaciju da unese proizvod u bazu podataka
     * 
     * @param productForInsert tipa Product, proizvod za unos u bazu podataka
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
    public void insertProduct(Product productForInsert) throws Exception {
        GenericSystemOperation SO = new InsertProduct();
        SO.execute(productForInsert);
    }
    /**
     * metoda koja poziva sistemsku operaciju da unese izvestaj sa svim stavkama
     *  u bazu podataka
     * 
     * @param reportForInsert tipa Report, izvestaj sa stavkama za unos u bazu podataka.
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
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
    /**
     * metoda koja poziva sistemsku operaciju da vrati sve poslovne partnere iz baze podataka
     * 
     * @return lista poslovnih partnera List<Partner>
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
    public List<Partner> getAllPartners() throws Exception {
//        List<Partner> partners;
//        partners = repositoryPartner.selectAll();
//        return partners;
          GenericSystemOperation SO = new SelectAllPartnersParam();
          SO.execute(new Partner());
          return ((SelectAllPartnersParam)SO).getPartners();
          
    }
    /**
     * metoda koja poziva sistemsku operaciju da vrati sve firme iz baze podataka
     * 
     * @return lista firmi List<Firm>
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
    public List<Firm> getAllFirms() throws Exception {
//        return repositoryFirm.selectAll();
        GenericSystemOperation SO = new SelectAllFirmsParam();
        SO.execute(new Firm());
        return ((SelectAllFirmsParam)SO).getFirms();
    }
    /**
     * metoda koja poziva sistemsku operaciju da unese prijemnicu sa svim stavkama u bazu podataka
     * 
     * @param noteForInsert tipa GoodsReceivedNote, prijemnica sa stavkama za unos u bazu podataka.
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
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
    /**
     * metoda koja poziva sistemsku operaciju da vrati sva fizicka lica iz baze podataka
     * 
     * @return lista fizickih lica kao List<NaturalPerson>
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
    public List<NaturalPerson> getAllNaturalPersons() throws Exception {
//        return repositoryNaturalPerson.selectAll();
        GenericSystemOperation SO = new SelectAllNaturalPersonsParam();
        SO.execute(new NaturalPerson());
        return ((SelectAllNaturalPersonsParam)SO).getNaturalPersons();
    }
    /**
     * metoda koja poziva sistemsku operaciju da vrati sva pravna lica iz baze podataka
     * 
     * @return lista pravnih lica kao List<NaturalPerson>
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
    public List<LegalPerson> getAllLegalPersons() throws Exception {
//        return repositoryLegalPerson.selectAll();
        GenericSystemOperation SO = new SelectAllLegalPersonsParam();
        SO.execute(new LegalPerson());
        return ((SelectAllLegalPersonsParam)SO).getLegalPersons();
        
    }
    /**
     * metoda koja poziva sistemsku operaciju da unese otpremnicu sa svim stavkama u bazu podataka
     * 
     * @param billForInsert tipa BillOfLading, otpremnica za unos u bazu podataka
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
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
    /**
     * metoda koja poziva sistemsku operaciju da vrati sve izvestaje sa stavkama sa zadatim parametrom
     * 
     * @param reportForSearch tipa Report, izvestaj sa informacijom na osnovu koje se pretrazuju izvestaji u bazi podataka
     * @return lista izvestaja List<Report>
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
    public List<Report> getAllReports(Report reportForSearch) throws Exception {
        GenericSystemOperation SO = new SelectAllReportsParam();
        SO.execute(reportForSearch);
        
        return ((SelectAllReportsParam)SO).getReports();
    }
    /**
     * metoda koja poziva sistemsku operaciju da vrati sve stavkame izvestaja iz baze podataka
     * 
     * @return lista stavki izvestaja kao List<ReportItem>
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
    public List<ReportItem> getAllReportItems() throws Exception {
        GenericSystemOperation SO = new SelectAllReportItemsParam();
        SO.execute(new ReportItem());
        return ((SelectAllReportItemsParam)SO).getItems();
    }
    /**
     * metoda za izmenu stanja na skladistu proizvoda nakon unosa prijemnice ili otpremnice. 
     * Otvorena mogucnost prosirenja na druge poslovne dokumente (menica, narudzbenica)
     * Implementirana preko Strategy Pattern-a
     * 
     * @param items tipa List<? extends AbstractDocumentItem>, lista apstraktnih stavki dokumenta
     * @throws Exception u slucaju greske prilikom transakcije (izvrsen rollback)
     */
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
