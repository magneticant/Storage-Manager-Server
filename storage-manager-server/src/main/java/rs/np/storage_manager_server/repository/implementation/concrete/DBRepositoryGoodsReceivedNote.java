//package repository.implementation.concrete;
package rs.np.storage_manager_server.repository.implementation.concrete;

import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import rs.np.storage_manager_common.domain.abstraction.AbstractDocumentItem;
import rs.np.storage_manager_common.domain.abstraction.implementation.GoodsReceivedNote;
import rs.np.storage_manager_common.domain.abstraction.implementation.GoodsReceivedNoteItem;
import rs.np.storage_manager_server.repository.DBBroker;
import rs.np.storage_manager_server.repository.DBRepository;
/**
 * Klasa koja predstavlja repozitorijum za prijemnicu, implementira interfejs {@link DBRepositoryLegalPerson} 
 * i prosledjuje {@link GoodsReceivedNote} kao genericki parametar
 * @author Milan
 */
public class DBRepositoryGoodsReceivedNote implements DBRepository<GoodsReceivedNote>{

    @Override
    public List<GoodsReceivedNote> selectAll(GoodsReceivedNote parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(GoodsReceivedNote parameter) throws Exception {
        System.out.println("ENTERED INSERT GOODS RECEIVED NOTE.");
            Connection conn = null;
          try {
            String query = "INSERT INTO " + parameter.getTableName() +
                             parameter.getColumnsWithoutID()+ " " +
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
                item.setDocumentID(parameter.getID());
            String query1 = "INSERT INTO " + ((GoodsReceivedNoteItem)item).getTableName() +
                              ((GoodsReceivedNoteItem)item).getColumnsWithoutID() +
                            "VALUES " + ((GoodsReceivedNoteItem)item).getInsertValues() + ";";
                System.out.println(query1);
                conn = DBBroker.getInstance().establishConnection();
                Statement statement1 = conn.createStatement();
                statement1.executeUpdate(query1);
            }
            
              System.out.println("Note inserted successfully!");
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
    public void update(GoodsReceivedNote parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(GoodsReceivedNote parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<GoodsReceivedNote> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
