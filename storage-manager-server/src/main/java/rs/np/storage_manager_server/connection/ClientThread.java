//package connection;
package rs.np.storage_manager_server.connection;
import rs.np.storage_manager_common.connection.abstraction.Receiver;
import rs.np.storage_manager_common.connection.abstraction.Request;
import rs.np.storage_manager_common.connection.abstraction.Response;
import rs.np.storage_manager_common.connection.abstraction.Sender;
import rs.np.storage_manager_common.connection.abstraction.JSONImpl.*;
import rs.np.storage_manager_common.connection.abstraction.objectImpl.RequestObject;
import rs.np.storage_manager_common.connection.abstraction.objectImpl.ResponseObject;
import rs.np.storage_manager_common.domain.*;
import rs.np.storage_manager_common.domain.abstraction.implementation.*;
import rs.np.storage_manager_common.domain.utility.TransferType;
import rs.np.storage_manager_server.controller.Controller;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import javax.sound.midi.Receiver;


/**
 * Predstavlja serversku klasu gde se izvrsava nit svakog klijenta na serveru.
 * 
 * 
 * @author Milan
 */
class ClientThread extends Thread {
	/**
	 * Privatni atribut socket (tipa {@link Socket}) koji predstavlja soket za rad sa klijentom.
	 */
    private Socket socket;
    /**
     * Privatni atribut sender ({@link Sender}) iz common jar-a koji sadrzi mehanizam za slanje "upakovanih" podataka (odgovora) klijentu.
     */
    private Sender sender;
    /**
     * Privatni atribut receiever ({@link Receiver}) iz common jar-a koji sadrzi mehanizam za prijem "upakovanih" podataka (odgovora) od klijenta.
     */
    private Receiver receiver;
    /**
     * Privatni atribut domenske klase {@link User}.
     */
    private User user;
    /**
     * Privatni atribut {@link Server} klase koja sadrzi metode za uspostavljanje konekcije i rad sa nitima klijenata.
     */
    private Server server;
    /**
     * Parametrizovani konstruktor klase {@link ClientThread}.
     * 
     * @param socket sluzi za inicijalizaciju soket-a za komunikaciju sa klijentom
     * @param server sluzi za inicijalizaciju server atributa klase, za rad sa nitima klijenta.
     */
    public ClientThread(Socket socket, Server server) {
        this.socket = socket;
        sender = new SenderJSON(socket);
        receiver = new ReceiverJSON(socket);
        this.server = server;
    }
    /**
     * Glavna run metoda ove klase (Override-ovana iz klase {@link Thread}) 
     * koja sluzi za prijem i razresavanje zahteva klijenata i prosledjivanje 
     * tih zahteva kontroleru.
     */
    @Override
    public void run() {
        while(true){
        	GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls()
        			.setDateFormat("yyyy-MM-dd");
            Gson gson = gsonBuilder.create();
            try {
            	
                Request req = receiver.receiveObject(RequestJSON.class);
//            	Request req = gson.fromJson(receiver.receiveObject().toString(), Request.class);
                Response resp = new ResponseJSON();    
                /*
                LOGIN, INSERT_PRODUCT, INSERT_NOTE, SELECT_ALL_PRODUCTS,
    SELECT_PODUCT, DELETE_PRODUCT, SELECT_NOTE, DELETE_NOTE, 
    UPDATE_PRODUCT, INSERT_REPORT
                */
                
                try{
                	
                    switch(req.getOperation()){
                        case LOGIN:
                        	
                        	User u = (User)req.getObj(User.class);
//                            User u = gson.fromJson(req.getObj().toString(), User.class);
                            
                            u = Controller.getInstance().login(u.getUsername(), u.getPassword());
                            System.out.println("USER IS " + u);
                            this.user = u;
                            checkForLoginStatus(u);
                            System.out.println("Passed the checks");
                            resp.setResponse(u, User.class);
                            
                            break;
                        case INSERT_PRODUCT:
                            System.out.println("ENTERED INSERT PRODUCT");
                            Product productForInsert = (Product)req.getObj(Product.class);
//                            Product productForInsert = gson.fromJson(req.getObj().toString(), Product.class);
                            Controller.getInstance().insertProduct(productForInsert);
                            break;
                        case INSERT_NOTE:
                            System.out.println("ENTERED INSERT NOTE");
                            GoodsReceivedNote noteForInsert = (GoodsReceivedNote)req.getObj(GoodsReceivedNote.class);
//                            System.out.println("1");
//                            System.out.println(req.getObj().getClass());
//                            System.out.println("2");
//                            GoodsReceivedNote noteForInsert = gson.fromJson(gson.toJson(req.getObj()),
//                            		GoodsReceivedNote.class);
//                            System.out.println(noteForInsert);
//                            System.out.println("3");
                            Controller.getInstance().insertGoodsReceivedNote(noteForInsert);
//                            System.out.println("4");
                            break;
                        case SELECT_ALL_PRODUCTS_PARAM:
                            System.out.println("RECOGNIZED SELECT ALL PRODUCTS PARAM");
                            List<Product> productsResponse;
                            Product productForSelect = (Product)req.getObj(Product.class);
//                            Product productForSelect = gson.fromJson(req.getObj().toString(), Product.class);
                            productsResponse = Controller.getInstance().getAllProducts(productForSelect);
                            System.out.println("Fetched all products from database.");
                            resp.setResponse(productsResponse, Product[].class);
                            break;
                        case SELECT_ALL_PRODUCTS:
                            System.out.println("RECOGNIZED SELECT ALL PRODUCTS");
                            List<Product> products;
                            
                            products = Controller.getInstance().getAllProducts();
                            System.out.println("Fetched all products from database.");
                            resp.setResponse(products, Product[].class );
                            break;
                        case SELECT_PRODUCT:
                            System.out.println("ENTERED SELECT PRODUCT");
                            Product productSelect = (Product)req.getObj(Product.class);
//                            Product productSelect = gson.fromJson(req.getObj().toString(), Product.class);
                            List<Product> productListByName;
                            
                            productListByName = Controller.getInstance().getAllProducts(productSelect);
                            System.out.println("Fetched list with one product from database according to Name.");
                            resp.setResponse(productListByName, Product[].class);
                            break;
                        case DELETE_PRODUCT:
                            System.out.println("ENTERED DELETE PRODUCT");
                            Product productForDeletion = (Product)req.getObj(Product.class);
//                            Product productForDeletion = gson.fromJson(req.getObj().toString(), Product.class);
                            
                            Controller.getInstance().deleteProduct(productForDeletion);
                            System.out.println("Product deleted successfully.");
                            resp.setResponse(null, Product.class);
                            break;
                        case SELECT_NOTE:
                            break;
                        case DELETE_NOTE:
                            break;
                        case UPDATE_PRODUCT:
                            System.out.println("ENTERED UPDATE PRODUCT");
                            Product productUpdate = (Product)req.getObj(Product.class);
//                            Product productUpdate = gson.fromJson(req.getObj().toString(), Product.class);
                            
                            Controller.getInstance().updateProduct(productUpdate);
                            System.out.println("Product updated.");
                            resp.setResponse(productUpdate, Product.class);
                            break;
                        case INSERT_REPORT:
                            System.out.println("ENTERED INSERT REPORT");
                            Report reportForInsertion = (Report)req.getObj(Report.class);
//                            System.out.println("****************");
//                    		System.out.println(req.getObj());
//                    		System.out.println("****************");
//                            Report reportForInsertion = gson.fromJson(req.getObj().toString(), Report.class);
                            Controller.getInstance().insertReport(reportForInsertion);
                            resp.setResponse(null, Report.class);
                            break;
                        case SELECT_ALL_PARTNERS:
                            System.out.println("ENTERED SELECT ALL PARTNERS");
                            List<Partner> partners;
                            partners = Controller.getInstance().getAllPartners();
                            System.out.println("Fetched all partners from database.");
                            resp.setResponse(partners, Partner[].class);
                            break;
                        case SELECT_ALL_FIRMS:
                            System.out.println("ENTERED SELECT ALL FIRMS");
                            List<Firm> firms;
                            firms = Controller.getInstance().getAllFirms();
                            System.out.println("Fetched all firms from database.");
                            resp.setResponse(firms, Firm[].class);
                            break;
                        case SELECT_ALL_NATURAL_PERSONS:
                            System.out.println("ENTERED SELECT ALL NATURAL PERSONS");
                            List<NaturalPerson> naturalPersons;
                            naturalPersons = Controller.getInstance().getAllNaturalPersons();
                            System.out.println("Fetched all natural persons from database.");
                            resp.setResponse(naturalPersons, NaturalPerson[].class);
                            break;
                        case SELECT_ALL_LEGAL_PERSONS:
                            System.out.println("ENTERED SELECT ALL LEGAL PERSONS");
                            List<LegalPerson> legalPersons = Controller.getInstance().getAllLegalPersons();
                            System.out.println("Fetched all legal persons from database.");
                            resp.setResponse(legalPersons, LegalPerson[].class);
                            break;
                        case INSERT_BILL:
                            System.out.println("ENTERED INSERT NOTE");
                            BillOfLading billForInsert = (BillOfLading)req.getObj(BillOfLading.class);
//                            BillOfLading billForInsert = gson.fromJson(
//                            		req.getObj().toString(), BillOfLading.class);
                            Controller.getInstance().insertBillOfLading(billForInsert);
                            break;
                        case SELECT_ALL_REPORTS:
                            System.out.println("ENTERED SELECT ALL REPORTS");
                            List<Report> reports;
                            reports = Controller.getInstance().getAllReports(new Report());
                            System.out.println("Fetched all reports from database.");
                            resp.setResponse(reports, Report[].class);
                            break;
                        case SELECT_ALL_REPORTS_PARAM:
                            System.out.println("ENTERED SELECT ALL REPORTS PARAM");
                            Report reportForSearch = (Report)req.getObj(Report.class);
//                            Report reportForSearch = gson.fromJson(req.getObj().toString(), Report.class);
                            reports = Controller.getInstance().getAllReports(reportForSearch);
                            resp.setResponse(reports, Report[].class);
                            break;
                        default: 
                            System.out.println("n/a");
                            break;
                    }
                }catch(Exception ex){
                    System.out.println("Client thread: CRUD Operation exception");
                    System.out.println(ex);
                    String message = ex.getMessage();
                    resp.setExMessage(message);
                }
//                System.out.println(gson.toJson(resp.getEx()));
//                System.out.println(gson.toJson(resp));
                sender.sendObject(resp);
                System.out.println("SENT!");
            } catch (Exception ex) {
                System.out.println("Request processing exception");
                ex.printStackTrace();
                break;
            }
        }
    }
    /**
     * get metoda za soket za komunikaciju.
     * 
     * @return socket tipa {@link Socket}.
     */
    public Socket getSocket() {
        return socket;
    }
    /**
     * get metoda za korisnika (klijenta)
     * 
     * @return klijent tipa {@link Client}
     */
    public User getUser() {
        return user;
    }
    /**
     * get metoda za posiljaoca (sender)
     * @return posiljalac (domenska klasa common projekta) kao tip {@link Sender}
     */
    public Sender getSender() {
        return sender;
    }
    /**
     * set metoda za posiljaoca (sender)
     * @param sender posiljalac tipa {@link Sender}
     */
    public void setSender(Sender sender) {
        this.sender = sender;
    }
    /**
     * get metoda za primaoca (receiver)
     * 
     * @return receiver primalac tipa {@link Receiver}
     */
    public Receiver getReceiver() {
        return receiver;
    }
    /**
     * set metoda za primaoca (receiver)
     * 
     * @param receiver primalac kao tip {@link Receiver}
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
    /**
     * Metoda koja proverava login status korisnika koji pokusava da se prijavi.
     * Ona otklanja sve korisnike koji imaju prazan username ili password ili koji su vec
     * prijavljeni na sistem.
     * 
     * @param user koji se prijavljuje na sistem (param tipa {@link User})
     * 
     * @throws Exception ako je uneti korisnik null.
     */
    private void checkForLoginStatus(User user) throws Exception {
        if(user == null) 
            throw new Exception("User cannot be null!");
        
        String username = user.getUsername();
        String password = user.getPassword();
        
        if(username == null || username.isEmpty()){
            server.activeClients.remove(this);
            System.out.println("Removed logged in client.");
            throw new Exception("Your username cannot be blank.");
        }
        
        if(password == null || username.isEmpty()){
            server.activeClients.remove(this);
            System.out.println("Removed logged in client.");
            throw new Exception("Your password cannot be blank.");
        }
        
        if(server.getActiveClients() == null || server.getActiveClients().size()<2){
            return;
        }
        for(ClientThread thread: server.getActiveClients()){
            if(thread == this)
                continue;
            if(thread.user.getPassword().equals(password) &&
                    thread.user.getUsername().equals(username)){
                server.activeClients.remove(this);
                System.out.println("Removed logged in client from server.");
                throw new Exception("You are already logged in.");
            }
        }
        }
    }

