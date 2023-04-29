//package repository.implementation.concrete;
package rs.np.storage_manager_server.repository.implementation.concrete;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import rs.np.storage_manager_common.domain.Firm;
import rs.np.storage_manager_server.repository.DBBroker;
import rs.np.storage_manager_server.repository.DBRepository;

/**
 *
 * @author Milan
 */
public class DBRepositoryFirm implements DBRepository<Firm>{

    @Override
    public List<Firm> selectAll(Firm parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Firm parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Firm parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Firm parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Firm> selectAll() {
        System.out.println("ENTERED SELECT ALL FIRM");
        try { 
            String query = "SELECT * FROM FIRMA WHERE TRUE;";
            System.out.println(query);
            List<Firm> firms = new ArrayList<>();
            Connection conn = DBBroker.getInstance().establishConnection();
            System.out.println("Connection established.");
            Statement st = conn.createStatement();
            System.out.println("Statement made.");
            ResultSet resultSet = st.executeQuery(query);
            System.out.println("Result set made " + resultSet);
            
            while(resultSet.next()){
                Firm firm = new Firm();
                firm.setID(resultSet.getInt("IDFirme"));
                firm.setFirmName(resultSet.getString("nazivFirme"));
                firm.setFirmAddress(resultSet.getString("adresaFirme"));
                firms.add(firm);
            }
                st.close();
                return firms;
        } catch (SQLException ex) {
            System.out.println("DBRepositoryUser: Something went wrong while fetching all partners.");
            return null;
        } catch (Exception ex) {
            System.out.println("EXCEPTION! " + ex.getMessage());
            Logger.getLogger(DBRepositoryUser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
