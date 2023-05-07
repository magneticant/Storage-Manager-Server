//package connection;
package rs.np.storage_manager_server.connection;
import rs.np.storage_manager_common.connection.ReceiverObject;
import rs.np.storage_manager_common.connection.ReceiverJSON;
import rs.np.storage_manager_common.connection.Request;
import rs.np.storage_manager_common.connection.Response;
import rs.np.storage_manager_common.connection.SenderJSON;
import rs.np.storage_manager_common.domain.*;
import rs.np.storage_manager_common.domain.abstraction.implementation.*;
import rs.np.storage_manager_server.controller.Controller;

import java.net.Socket;
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
    private SenderJSON sender;
    /**
     * Privatni atribut receiever ({@link Receiver}) iz common jar-a koji sadrzi mehanizam za prijem "upakovanih" podataka (odgovora) od klijenta.
     */
    private ReceiverJSON receiver;
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
        	Gson gson = new GsonBuilder().serializeNulls().create();
            try {
            	
//                Request req = (Request)receiver.receiveObject();
            	Request req = gson.fromJson((String)receiver.receiveObject(), Request.class);
                Response resp = new Response();                /*
                LOGIN, INSERT_PRODUCT, INSERT_NOTE, SELECT_ALL_PRODUCTS,
    SELECT_PODUCT, DELETE_PRODUCT, SELECT_NOTE, DELETE_NOTE, 
    UPDATE_PRODUCT, INSERT_REPORT
                */
                
                try{
                	
                    switch(req.getOperation()){
                        case LOGIN:
                        	
//                        	User u = (User) req.getObj();
                            User u = gson.fromJson(req.getObj().toString(), User.class);
                            
                            u = Controller.getInstance().login(u.getUsername(), u.getPassword());
                            System.out.println("USER IS " + u);
                            this.user = u;
                            checkForLoginStatus(u);
                            System.out.println("Passed the checks");
                            resp.setResponse(u);
                            
                            break;
                        case INSERT_PRODUCT:
                            System.out.println("ENTERED INSERT PRODUCT");
                            Product productForInsert = (Product)req.getObj();
                            Controller.getInstance().insertProduct(productForInsert);
                            break;
                        case INSERT_NOTE:
                            System.out.println("ENTERED INSERT NOTE");
                            GoodsReceivedNote noteForInsert = (GoodsReceivedNote)req.getObj();
                            Controller.getInstance().insertGoodsReceivedNote(noteForInsert);
                            break;
                        case SELECT_ALL_PRODUCTS_PARAM:
                            System.out.println("RECOGNIZED SELECT ALL PRODUCTS PARAM");
                            List<Product> productsResponse;
                            Product productForSelect = (Product)req.getObj();
                            productsResponse = Controller.getInstance().getAllProducts(productForSelect);
                            System.out.println("Fetched all products from database.");
                            resp.setResponse(productsResponse);
                            break;
                        case SELECT_ALL_PRODUCTS:
                            System.out.println("RECOGNIZED SELECT ALL PRODUCTS");
                            List<Product> products;
                            
                            products = Controller.getInstance().getAllProducts();
                            System.out.println("Fetched all products from database.");
                            resp.setResponse(products);
                            break;
                        case SELECT_PRODUCT:
                            System.out.println("ENTERED SELECT PRODUCT");
                            Product productSelect = (Product)req.getObj();
                            List<Product> productListByName;
                            
                            productListByName = Controller.getInstance().getAllProducts(productSelect);
                            System.out.println("Fetched list with one product from database according to Name.");
                            resp.setResponse(productListByName);
                            break;
                        case DELETE_PRODUCT:
                            System.out.println("ENTERED DELETE PRODUCT");
                            Product productForDeletion = (Product)req.getObj();
                            
                            Controller.getInstance().deleteProduct(productForDeletion);
                            System.out.println("Product deleted successfully.");
                            resp.setResponse(null);
                            break;
                        case SELECT_NOTE:
                            break;
                        case DELETE_NOTE:
                            break;
                        case UPDATE_PRODUCT:
                            System.out.println("ENTERED UPDATE PRODUCT");
                            Product productUpdate = (Product)req.getObj();
                            
                            Controller.getInstance().updateProduct(productUpdate);
                            System.out.println("Product updated.");
                            resp.setResponse(productUpdate);
                            break;
                        case INSERT_REPORT:
                            System.out.println("ENTERED INSERT REPORT");
                            Report reportForInsertion = (Report)req.getObj();
                            Controller.getInstance().insertReport(reportForInsertion);
                            resp.setResponse(null);
                            break;
                        case SELECT_ALL_PARTNERS:
                            System.out.println("ENTERED SELECT ALL PARTNERS");
                            List<Partner> partners;
                            partners = Controller.getInstance().getAllPartners();
                            System.out.println("Fetched all partners from database.");
                            resp.setResponse(partners);
                            break;
                        case SELECT_ALL_FIRMS:
                            System.out.println("ENTERED SELECT ALL FIRMS");
                            List<Firm> firms;
                            firms = Controller.getInstance().getAllFirms();
                            System.out.println("Fetched all firms from database.");
                            resp.setResponse(firms);
                            break;
                        case SELECT_ALL_NATURAL_PERSONS:
                            System.out.println("ENTERED SELECT ALL NATURAL PERSONS");
                            List<NaturalPerson> naturalPersons;
                            naturalPersons = Controller.getInstance().getAllNaturalPersons();
                            System.out.println("Fetched all natural persons from database.");
                            resp.setResponse(naturalPersons);
                            break;
                        case SELECT_ALL_LEGAL_PERSONS:
                            System.out.println("ENTERED SELECT ALL LEGAL PERSONS");
                            List<LegalPerson> legalPersons = Controller.getInstance().getAllLegalPersons();
                            System.out.println("Fetched all legal persons from database.");
                            resp.setResponse(legalPersons);
                            break;
                        case INSERT_BILL:
                            System.out.println("ENTERED INSERT NOTE");
                            BillOfLading billForInsert = (BillOfLading)req.getObj();
                            Controller.getInstance().insertBillOfLading(billForInsert);
                            break;
                        case SELECT_ALL_REPORTS:
                            System.out.println("ENTERED SELECT ALL REPORTS");
                            List<Report> reports;
                            reports = Controller.getInstance().getAllReports(new Report());
                            System.out.println("Fetched all reports from database.");
                            resp.setResponse(reports);
                            break;
                        case SELECT_ALL_REPORTS_PARAM:
                            System.out.println("ENTERED SELECT ALL REPORTS PARAM");
                            Report reportForSearch = (Report)req.getObj();
                            reports = Controller.getInstance().getAllReports(reportForSearch);
                            resp.setResponse(reports);
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
                sender.sendObject(gson.toJson(resp));
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
    public SenderJSON getSender() {
        return sender;
    }
    /**
     * set metoda za posiljaoca (sender)
     * @param sender posiljalac tipa {@link Sender}
     */
    public void setSender(SenderJSON sender) {
        this.sender = sender;
    }
    /**
     * get metoda za primaoca (receiver)
     * 
     * @return receiver primalac tipa {@link Receiver}
     */
    public ReceiverJSON getReceiver() {
        return receiver;
    }
    /**
     * set metoda za primaoca (receiver)
     * 
     * @param receiver primalac kao tip {@link Receiver}
     */
    public void setReceiver(ReceiverJSON receiver) {
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

