//package repository.implementation.concrete;
package rs.np.storage_manager_server.repository.implementation.concrete;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import rs.np.storage_manager_common.domain.abstraction.AbstractDocumentItem;
import rs.np.storage_manager_common.domain.abstraction.implementation.BillOfLading;
import rs.np.storage_manager_common.domain.abstraction.implementation.BillOfLadingItem;
import rs.np.storage_manager_server.repository.DBBroker;
import rs.np.storage_manager_server.repository.DBRepository;

/**
 * Klasa koja predstavlja repozitorijum za otpremnicu, implementira interfejs DBRepository 
 * i prosledjuje {@link BillOfLading} kao genericki parametar
 * @author Milan
 */
public class DBRepositoryBillOfLading implements DBRepository<BillOfLading>{
	
    @Override
    public List<BillOfLading> selectAll(BillOfLading parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(BillOfLading parameter) throws Exception {
        System.out.println("ENTERED INSERT BILL OF LADING.");
            Connection conn = null;
          try {
              
            String query = "INSERT INTO " + parameter.getTableName() +
                              "(" + parameter.getColumnsWithoutID()+ ") " +
                            "VALUES " + parameter.getInsertValues() + ";";
                    
            System.out.println(query);
            conn = DBBroker.getInstance().establishConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
            
              ResultSet key = statement.getGeneratedKeys();
              if(key.next()){
                  Integer ID = key.getInt(1);
                  parameter.setID(ID);
              }
            
            for(AbstractDocumentItem item : parameter.getItems()) {
                System.out.println(item);
                item.setDocument(parameter);
            String query1 = "INSERT INTO " + ((BillOfLadingItem)item).getTableName() +
                              " ("+((BillOfLadingItem)item).getColumnsWithoutID() + " )" +
                            "VALUES " + ((BillOfLadingItem)item).getInsertValues() + ";";
                System.out.println(query1);
                conn = DBBroker.getInstance().establishConnection();
                Statement statement1 = conn.createStatement();
                statement1.executeUpdate(query1);
            }
            
              System.out.println("Bill inserted successfully!");
//              conn.commit();
              statement.close();
        } catch (SQLException ex) {
              System.out.println("DBRepositoryProduct: Something went wrong while "
                      + "inserting goods received note.");
            Logger.getLogger(DBRepositoryProduct.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public void update(BillOfLading parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(BillOfLading parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<BillOfLading> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
