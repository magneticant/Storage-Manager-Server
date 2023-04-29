//package repository;
package rs.np.storage_manager_server.repository;

//import domain.Product;
//import domain.WhereClauseMode;
import java.util.List;

/**
 *
 * @author Milan
 */
public interface Repository<T> {
    List<T> selectAll(T parameter) throws Exception;
    void insert(T parameter) throws Exception;
    void update(T parameter) throws Exception;
    void delete(T parameter) throws Exception;
    List<T> selectAll();
}
