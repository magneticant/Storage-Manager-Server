//package repository.implementation.concrete;
package rs.np.storage_manager_server.repository.implementation.concrete;

import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import rs.np.storage_manager_common.domain.Report;
import rs.np.storage_manager_common.domain.ReportItem;
import rs.np.storage_manager_server.repository.DBBroker;
import rs.np.storage_manager_server.repository.DBRepository;
/**
 *
 * @author Milan
 */
public class DBRepositoryReport implements DBRepository<Report>{

    @Override
    public List<Report> selectAll(Report parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Report parameter) throws Exception {
        System.out.println("ENTERED INSERT REPORT.");
            Connection conn = null;
          try {
            String query = "INSERT INTO " + parameter.getTableName() +
                             " (" + parameter.getColumnNames()+ ") " +
                            "VALUES " + parameter.getInsertValues() + ";";
                    
            System.out.println(query);
            conn = DBBroker.getInstance().establishConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            //ReportItem item = new ReportItem();
            
            for(ReportItem item : parameter.getReportItems()) {
            String query1 = "INSERT INTO " + item.getTableName() +
                             " (" + item.getColumnsWithoutID() + ") " +
                            "VALUES " + item.getInsertValues() + ";";
                System.out.println(query1);
                conn = DBBroker.getInstance().establishConnection();
                Statement statement1 = conn.createStatement();
                statement1.executeUpdate(query1);
            }
            
              System.out.println("Report inserted successfully!");
//              conn.commit();
              statement.close();
        } catch (SQLException ex) {
              System.out.println("DBRepositoryProduct: Something went wrong while inserting report.");
            Logger.getLogger(DBRepositoryProduct.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public void update(Report parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Report parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Report> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    

}
