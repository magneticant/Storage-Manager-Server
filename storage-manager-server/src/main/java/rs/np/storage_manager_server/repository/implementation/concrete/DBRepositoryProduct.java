//package repository.implementation.concrete;
package rs.np.storage_manager_server.repository.implementation.concrete;

import java.util.List;
import rs.np.storage_manager_common.domain.Product;
import rs.np.storage_manager_common.domain.ProductType;
import rs.np.storage_manager_server.repository.DBBroker;
import rs.np.storage_manager_server.repository.DBRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Klasa koja predstavlja repozitorijum za proizvod, implementira interfejs {@link DBRepository} 
 * i prosledjuje {@link Product} kao genericki parametar
 * @author Milan
 */
public class DBRepositoryProduct implements DBRepository<Product>{

    @Override
    public List<Product> selectAll(Product parameter) throws Exception{
        System.out.println("ENTERED SELECT ALL");
        try {
            String query = "SELECT " + parameter.getColumnNames() +
                            " FROM " + parameter.getTableName() +
                            " WHERE " + parameter.getWhereCondition(parameter.getMode()) + ";";
            System.out.println(query);
            List<Product> products = new ArrayList<>();
            Connection conn = DBBroker.getInstance().establishConnection();
            Statement st = conn.createStatement();
            System.out.println("STATEMENT MADE");
            ResultSet resultSet = st.executeQuery(query);
            System.out.println("RESULTSET MADE");
            System.out.println(resultSet);
            
            while(resultSet.next()){
                Product product = new Product();
                product.setID(resultSet.getInt("sifraArtikla"));
                product.setProductName(resultSet.getString("imeArtikla"));
                product.setWeight(resultSet.getDouble("gramaza"));
                product.setFragile(resultSet.getBoolean("lomljiv"));
                product.setAmount(resultSet.getInt("kolicina"));
                product.setType(ProductType.valueOf(resultSet.getString("tip")));
                product.setPrice(resultSet.getBigDecimal("cena"));
                
                products.add(product);
            }
            st.close();
            for(Product p : products){
                System.out.println(p);
            }
            return products;
        } catch (SQLException ex) {
            System.out.println("DBRepositoryProduct: Something went wrong while fetching all products.");
            
            Logger.getLogger(DBRepositoryUser.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("User not found.");
            //return null;
        }
         
    }

    @Override
    public void insert(Product parameter) throws Exception {
            System.out.println("ENTERED INSERT PRODUCT.");
            Connection conn = null;
          try {
            String query = "INSERT INTO " + parameter.getTableName() +
                             " (" + parameter.getColumnsWithoutID() + ") " +
                            "VALUES " + parameter.getInsertValues() + ";";
                    
            System.out.println(query);
            conn = DBBroker.getInstance().establishConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            
              System.out.println("Product inserted successfully!");
              conn.commit();
              statement.close();
        } catch (SQLException ex) {
              System.out.println("DBRepositoryProduct: Something went wrong while inserting product.");
            Logger.getLogger(DBRepositoryProduct.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public void update(Product parameter) throws Exception {
        System.out.println("ENTERED UPDATE PRODUCT");
        Connection conn = null;
        try {
            String query = "UPDATE " + parameter.getTableName() +
                            " SET " + parameter.getValues() +
                             " WHERE " + parameter.getWhereCondition(parameter.getMode()) + ";";
           
            System.out.println(query);
//            List<Product> products = new ArrayList<>();
            conn = DBBroker.getInstance().establishConnection();
            Statement st = conn.createStatement();
            System.out.println("STATEMENT MADE");
            st.executeUpdate(query);
            conn.commit();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println("DBRepositoryProduct: Something went wrong while updating product.");
            conn.rollback();
            Logger.getLogger(DBRepositoryUser.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("User not found.");
            //return null;
        }
    }

    @Override
    public void delete(Product parameter)  throws Exception{
            System.out.println("ENTERED DELEETE PRODUCT.");
            Connection conn = null;
          try {
            String query = "DELETE "
                    + "FROM " + parameter.getTableName()
                    + " WHERE " + parameter.getWhereCondition(parameter.getMode()) + ";";
            System.out.println(query);
            conn = DBBroker.getInstance().establishConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            
              System.out.println("Product deleted successfully!");
              conn.commit();
              statement.close();
        } catch (SQLException ex) {
              System.out.println("DBRepositoryProduct: Something went wrong while deleting product.");
            Logger.getLogger(DBRepositoryProduct.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public List<Product> selectAll() {
        System.out.println("ENTERED SELECT ALL");
        try {
            String query = "SELECT * FROM ARTIKAL";
            List<Product> products = new ArrayList<>();
            Connection conn = DBBroker.getInstance().establishConnection();
            Statement st = conn.createStatement();
            System.out.println("STATEMENT MADE");
            ResultSet resultSet = st.executeQuery(query);
            System.out.println("RESULTSET MADE");
            System.out.println(resultSet);
            
            while(resultSet.next()){
                Product product = new Product();
                product.setID(resultSet.getInt("sifraArtikla"));
                product.setProductName(resultSet.getString("imeArtikla"));
                product.setWeight(resultSet.getDouble("gramaza"));
                product.setFragile(resultSet.getBoolean("lomljiv"));
                product.setAmount(resultSet.getInt("kolicina"));
                product.setType(ProductType.valueOf(resultSet.getString("tip")));
                product.setPrice(resultSet.getBigDecimal("cena"));
                
                products.add(product);
            }
            st.close();
            for(Product p : products){
                System.out.println(p);
            }
            return products;
        } catch (SQLException ex) {
            System.out.println("DBRepositoryProduct: Something went wrong while fetching all products.");
            Logger.getLogger(DBRepositoryUser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(DBRepositoryProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

   
    
}
