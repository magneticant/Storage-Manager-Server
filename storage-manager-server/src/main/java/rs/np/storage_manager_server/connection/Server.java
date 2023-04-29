//package connection;
package rs.np.storage_manager_server.connection;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import rs.np.storage_manager_common.domain.User;
/**
 * Server klasa (koja je tipa Thread) i koja obezbedjuje okvir za rad sa klijent nitima.
 * Omogucava uspostavljanje servera, osluskivanje, diskonektovanje korisnika, dobijanje informacija o aktivnim korisnicima 
 * i rad sa klijent nitima.
 * 
 * @author Milan
 */
public class Server extends Thread {
	/**
	 * privatni atribut tipa ServerSocket koji predstavlja zacetak servera
	 */
    private ServerSocket ss;
    /**
     * package scope atribut tipa List<ClientThread> koji u sebi cuva informaciju o aktivnim klijentima
     */
    List<ClientThread> activeClients;
    /**
     * privatni atribut socket koji predstavlja objekat koji nastaje povezivanjem klijenta na server, sa serverske strane.
     */
    private Socket socket;
    /**
     * Metoda za uspostavljanje servera.
     * 
     * @throws IOException ako je zauzet trenutni port br. 6969.
     */
    public void establishServer() throws IOException{
        ss = new ServerSocket(6969);
        System.out.println("Server established on port 6969.");
        activeClients = new ArrayList<>();
    }
    /**
     * Glavna run metoda ove Thread klase. Sluzi za osluskivanje i cekanje klijenata da se povezu, i pokretanje njihovih niti.
     */
    @Override
    public void run() {
        while(!ss.isClosed()){
            try{
                System.out.println("Waiting for clients.");
                socket = ss.accept();
                System.out.println("New client just got connected!");
                workWithClient();
            }catch(Exception ex){
                System.out.println("Server thread exception");
            }
        }
        disconnectClients();
    }
    /**
     * Metoda koja stvara novu nit klijenta, prosledjuje joj soket za komunikaciju i referencu na this,
     *  i dodaje nit klijenta u listu aktivnih korisnika.
     */
    private void workWithClient() {
        ClientThread newUser = new ClientThread(socket, this);
        activeClients.add(newUser);
        newUser.start();
    }
    /**
     * Metoda za disconnect svih klijenata. Disconnect-uje sve klijente (korisnike) sa servera.
     * Obradjuje slucaj da je doslo do greske prilikom zatvaranja socket-a.
     */
    private void disconnectClients() {
        for(int i = 0;i<activeClients.size();i++) {
            try {
            activeClients.get(i).getSocket().close();
            activeClients.remove(i);
            } catch(IOException ex) {
                System.out.println("Socket closing error. Socket not closed properly");
                System.out.println(ex);
            }
        }
    }
    /**
     * get metoda za aktivne korisnike.
     * "Pakuje" sve korisnike koji su trenutno u internoj listi i salje ih kao listu
     * "aktivnih" korisnika.
     * 
     * @return lista korisnika (User), tipa ArrayList.
     */
    public List<User> getActiveUsers(){
        List<User> users = new ArrayList<>();
        for(int i = 0;i<activeClients.size();i++){
            users.add(activeClients.get(i).getUser());
        }
        
        return users;
    }
    /**
     * get metoda za ServerSocket
     * 
     * @return server soket kao tip ServerSocket
     */
    public ServerSocket getServerSocket() {
        return ss;
    }
    /**
     * set metoda za ServerSocket
     * 
     * @param ss server soket kao tip ServerSocket
     */
    public void setServerSocket(ServerSocket ss) {
        this.ss = ss;
    }
    /**
     * get metoda za niti aktivnih klijenata
     * 
     * @return aktivni klijenti kao tip List<ClientThread>. Ovde se vracaju niti klijenata, a ne lista objekata tipa Client
     */
    public List<ClientThread> getActiveClients() {
        return activeClients;
    }
    /**
     * set metoda za niti aktivnih klijenata
     * 
     * @param activeClients aktivni klijenti kao tip List<ClientThread>. Ovde se prosledjuju niti klijenata, a ne lista objekata tipa Client
     */
    public void setActiveClients(List<ClientThread> activeClients) {
        this.activeClients = activeClients;
    }
    /**
     * get metoda za soket
     * 
     * @return soket kao tip Socket
     */
    public Socket getSocket() {
        return socket;
    }
    /**
     * set metoda za soket
     * 
     * @param socket kao tip Socket
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
}
