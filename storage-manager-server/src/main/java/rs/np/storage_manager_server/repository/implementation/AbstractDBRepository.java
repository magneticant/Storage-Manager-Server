//package repository.implementation;
package rs.np.storage_manager_server.repository.implementation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import rs.np.storage_manager_common.domain.DomainClass;
import rs.np.storage_manager_server.repository.DBBroker;
import rs.np.storage_manager_server.repository.DBRepository;

/**
 *
 * @author Milan
 */
public class AbstractDBRepository implements DBRepository<DomainClass>{

    @Override
    public void insert(DomainClass parameter) throws Exception {
        System.out.println("STARTED OPERATION INSERT.");
//        System.out.println(parameter.setID(0));
        try {
        Connection connection = DBBroker.getInstance().establishConnection();
        String query = "INSERT INTO " + parameter.getTableName() +
                "(" + parameter.getColumnsWithoutID()+ ")" +
                " VALUES " + parameter.getInsertValues() + ";";
        System.out.println("QUERY: " + query);
        Statement st = connection.createStatement();
        st.executeUpdate(query);
        
        System.out.println("Object inserted successfully!");
        st.close();
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void update(DomainClass parameter) throws Exception {
        System.out.println("STARTED OPERATION UPDATE.");
        try {
        Connection connection = DBBroker.getInstance().establishConnection();
         String query = "UPDATE " + parameter.getTableName() +
                            " SET " + parameter.getValues() +
                             " WHERE " + parameter.getWhereCondition(parameter.getMode()) + ";";
        System.out.println("QUERY: " + query);
        Statement st = connection.createStatement();
        System.out.println("STATEMENT MADE");
        st.executeUpdate(query);
        System.out.println("Object updated successfully!");
        st.close();
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void delete(DomainClass parameter) throws Exception {
        System.out.println("STARTED OPERATION DELETE.");
        try {
        Connection connection = DBBroker.getInstance().establishConnection();
          String query = "DELETE "
                    + "FROM " + parameter.getTableName()
                    + " WHERE " + parameter.getWhereCondition(parameter.getMode()) + ";";
        System.out.println("QUERY: " + query);
        Statement st = connection.createStatement();
        System.out.println("STATEMENT MADE");
        st.executeUpdate(query);
        
        System.out.println("Object deleted successfully!");
        st.close();
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            throw ex;
        }
    }
    
    @Override
    public List<DomainClass> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DomainClass> selectAll(DomainClass parameter) throws Exception {
        List<DomainClass> allEntries = new ArrayList<>();
        System.out.println("STARTED OPERATION SELECT ALL.");
        try {
        Connection connection = DBBroker.getInstance().establishConnection();
          String query = "SELECT * FROM " + parameter.getTableName() + " WHERE "
                  + parameter.getWhereCondition(parameter.getMode()) + ";";
        System.out.println("QUERY: " + query);
        Statement st = connection.createStatement();
        System.out.println("STATEMENT MADE");
        ResultSet rs = st.executeQuery(query);
        
        while(rs.next()){
            DomainClass object = parameter.selectObject(rs);
            System.out.println("ADDING OBJECT: "+ object);
            allEntries.add(object);
        }
        
        System.out.println("Objects fetched successfully!");
        st.close();
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            throw ex;
        }
        
        return allEntries;
    }
    
}

