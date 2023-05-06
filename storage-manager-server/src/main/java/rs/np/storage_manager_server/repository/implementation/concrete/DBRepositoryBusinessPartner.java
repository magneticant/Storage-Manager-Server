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

import rs.np.storage_manager_common.domain.Partner;
import rs.np.storage_manager_server.repository.DBBroker;
import rs.np.storage_manager_server.repository.DBRepository;

/**
 * Klasa koja predstavlja repozitorijum za poslovnog partnera, implementira interfejs {@link DBRepository} 
 * i prosledjuje {@link Partner} kao genericki parametar
 * @author Milan
 */
public class DBRepositoryBusinessPartner implements DBRepository<Partner>{

    @Override
    public List<Partner> selectAll(Partner parameter) throws Exception {
        try { 
            String query = "SELECT " + parameter.getColumnNames()
                    + " FROM " + parameter.getTableName() +
                    " WHERE " + parameter.getWhereCondition(parameter.getMode()) + ";";
            System.out.println(query);
            List<Partner> partners = new ArrayList<>();
            Connection conn = DBBroker.getInstance().establishConnection();
            System.out.println("Connection established.");
            Statement st = conn.createStatement();
            System.out.println("Statement made.");
            ResultSet resultSet = st.executeQuery(query);
            System.out.println("Result set made " + resultSet);
            
            while(resultSet.next()){
                Partner partner = new Partner();
                System.out.println("PARTNER ID: " + resultSet.getInt("ID") );
                System.out.println("2");
                partner.setID(resultSet.getInt("IDPartnera"));
                System.out.println(resultSet.getInt("IDPartnera"));
                partner.setBusinessPartnerName(resultSet.getString("nazivFirmePart"));
                partner.setBusinessPartnerAddress(resultSet.getString("adresaPP"));
                partners.add(partner);
            }
                st.close();
                return partners;
        } catch (SQLException ex) {
            System.out.println("DBRepositoryUser: Something went wrong while fetching all partners.");
            return null;
        } catch (Exception ex) {
            System.out.println("EXCEPTION! " + ex.getMessage());
            Logger.getLogger(DBRepositoryUser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void insert(Partner parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Partner parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Partner parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Partner> selectAll() {
        System.out.println("ENTERED SELECT ALL PARTNERS");
        try { 
            String query = "SELECT * FROM PARTNER WHERE TRUE;";
            System.out.println(query);
            List<Partner> partners = new ArrayList<>();
            Connection conn = DBBroker.getInstance().establishConnection();
            System.out.println("Connection established.");
            Statement st = conn.createStatement();
            System.out.println("Statement made.");
            ResultSet resultSet = st.executeQuery(query);
            System.out.println("Result set made " + resultSet);
            
            while(resultSet.next()){
                Partner partner = new Partner();
                partner.setID(resultSet.getInt("IDPartnera"));
                partner.setBusinessPartnerName(resultSet.getString("nazivFirmePart"));
                partner.setBusinessPartnerAddress(resultSet.getString("adresaPP"));
                partners.add(partner);
            }
                st.close();
                return partners;
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
