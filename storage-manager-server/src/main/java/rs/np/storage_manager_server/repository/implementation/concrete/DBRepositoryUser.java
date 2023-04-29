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

import rs.np.storage_manager_common.domain.User;
import rs.np.storage_manager_server.repository.DBBroker;
import rs.np.storage_manager_server.repository.DBRepository;


/**
 *
 * @author Milan
 */
public class DBRepositoryUser implements DBRepository<User>{

    //IZMENI!!!
    @Override
    public List<User> selectAll(User parameter) {
        try { 
            String query = "SELECT " + parameter.getColumnNames()
                    + " FROM " + parameter.getTableName() +
                    " WHERE " + parameter.getWhereCondition(parameter.getMode()) + ";";
            System.out.println(query);
            List<User> users = new ArrayList<>();
            Connection conn = DBBroker.getInstance().establishConnection();
            System.out.println("Connection established.");
            Statement st = conn.createStatement();
            System.out.println("Statement made.");
            ResultSet resultSet = st.executeQuery(query);
            System.out.println("Result set made " + resultSet);
            
            while(resultSet.next()){
                User user = new User();
                System.out.println("USER ID: " + resultSet.getInt("ID") );
                System.out.println("2");
                user.setID(resultSet.getInt("ID"));
                System.out.println(resultSet.getInt("ID"));
                user.setName(resultSet.getString("ime"));
                user.setLastName(resultSet.getString("prezime"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                users.add(user);
            }
                st.close();
                return users;
        } catch (SQLException ex) {
            System.out.println("DBRepositoryUser: Something went wrong while fetching all users.");
            return null;
        } catch (Exception ex) {
            System.out.println("EXCEPTION! " + ex.getMessage());
            Logger.getLogger(DBRepositoryUser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }

    @Override
    public void insert(User parameter) throws Exception {
        //Unneeded operation
    }

    @Override
    public void update(User parameter) throws Exception {
        //Unneeded operation
    }

    @Override
    public void delete(User parameter) throws Exception {
        //unnneeded operation
    }

    @Override
    public List<User> selectAll() {
         
        try {
            String query = "SELECT * FROM KORISNIK";
            List<User> users = new ArrayList<>();
            Connection conn = DBBroker.getInstance().establishConnection();
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery(query);
            System.out.println(resultSet);
            
            while(resultSet.next()){
                User user = new User();
                user.setID(resultSet.getInt("ID"));
                user.setName(resultSet.getString("ime"));
                user.setLastName(resultSet.getString("prezime"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                users.add(user);
            }
            st.close();
            return users;
        } catch (SQLException ex) {
            System.out.println("DBRepositoryUser: Something went wrong while fetching all users.");
            Logger.getLogger(DBRepositoryUser.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (Exception ex) {
            Logger.getLogger(DBRepositoryUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }

}
