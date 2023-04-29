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
 *
 * @author Milan
 */
public class Server extends Thread {
    private ServerSocket ss;
    List<ClientThread> activeClients;
    private Socket socket;
    
    public void establishServer() throws IOException{
        ss = new ServerSocket(6969);
        System.out.println("Server established on port 6969.");
        activeClients = new ArrayList<>();
    }

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

    private void workWithClient() {
        ClientThread newUser = new ClientThread(socket, this);
        activeClients.add(newUser);
        newUser.start();
    }

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
    
    public List<User> getActiveUsers(){
        List<User> users = new ArrayList<>();
        for(int i = 0;i<activeClients.size();i++){
            users.add(activeClients.get(i).getUser());
        }
        
        return users;
    }

    public ServerSocket getServerSocket() {
        return ss;
    }

    public void setServerSocket(ServerSocket ss) {
        this.ss = ss;
    }

    public List<ClientThread> getActiveClients() {
        return activeClients;
    }

    public void setActiveClients(List<ClientThread> activeClients) {
        this.activeClients = activeClients;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
}
