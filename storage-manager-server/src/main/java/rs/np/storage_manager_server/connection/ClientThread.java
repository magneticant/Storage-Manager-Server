//package connection;
package rs.np.storage_manager_server.connection;
import rs.np.storage_manager_common.connection.Receiever;
import rs.np.storage_manager_common.connection.Request;
import rs.np.storage_manager_common.connection.Response;
import rs.np.storage_manager_common.connection.Sender;
import rs.np.storage_manager_common.domain.*;
import rs.np.storage_manager_common.domain.abstraction.implementation.*;
import rs.np.storage_manager_server.controller.Controller;

import java.net.Socket;
import java.util.List;
/**
 *
 * @author Milan
 */
class ClientThread extends Thread {
    private Socket socket;
    private Sender sender;
    private Receiever receiver;
    private User user;
    private Server server;
    
    public ClientThread(Socket socket, Server server) {
        this.socket = socket;
        sender = new Sender(socket);
        receiver = new Receiever(socket);
        this.server = server;
    }

    @Override
    public void run() {
        while(true){
            try {
                Request req = (Request)receiver.receiveObject();
                Response resp = new Response();                /*
                LOGIN, INSERT_PRODUCT, INSERT_NOTE, SELECT_ALL_PRODUCTS,
    SELECT_PODUCT, DELETE_PRODUCT, SELECT_NOTE, DELETE_NOTE, 
    UPDATE_PRODUCT, INSERT_REPORT
                */
                
                try{
                    switch(req.getOperation()){
                        case LOGIN:
                            User u = (User) req.getObj();
                            
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
                    resp.setEx(ex);
                }
                System.out.println("SENDING OBJECT: " + resp.getResponse());
                System.out.println("EXCEPTION CONTENT: " + resp.getEx());
                sender.sendObject(resp);
                System.out.println("SENT!");
            } catch (Exception ex) {
                System.out.println("Request processing exception");
                break;
            }
        }
    }
    
    public Socket getSocket() {
        return socket;
    }

    public User getUser() {
        return user;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Receiever getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiever receiver) {
        this.receiver = receiver;
    }

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

